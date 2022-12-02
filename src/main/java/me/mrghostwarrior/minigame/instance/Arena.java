package me.mrghostwarrior.minigame.instance;

import me.mrghostwarrior.minigame.GameState;
import me.mrghostwarrior.minigame.MiniGame;
import me.mrghostwarrior.minigame.manager.ConfigManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private MiniGame minigame;

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;
    private Countdown countdown;
    private Game game;

    public Arena (MiniGame minigame, int id, Location spawn){
        this.minigame = minigame;
        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(minigame, this);
        //here is where you add more games
        this.game = new Game(this);
    }
    /*-------------------------
              PLAYERS
    -------------------------*/

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state == GameState.RECRUITING && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle(" ", " ", 2, 2,2);
        if (state == GameState.COUNTDOWN && players.size() < ConfigManager.getMinimunPlayers()) {
            sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&c&lThere is not enough players. Countdown stopped." ));
            reset(false);
        }

        if (state == GameState.LIVE && players.size() < ConfigManager.getMinimunPlayers()) {
            sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&c&lThe game has ended as too many players have left." ));
            reset(true);
        }
    }

    /*-------------------------
                INFO
    -------------------------*/
    public int getId() { return id; }

    public GameState getState() { return  state; }

    public String getStateName() {
        GameState state = getState();
        if (state == GameState.RECRUITING){
            return "&2&RECRUITING";
        }
        else if (state == GameState.COUNTDOWN) {
            return "&c&lCOUNTDOWN";
        }
        else if (state == GameState.LIVE){
            return "&4&lPROGRESS";
            }
        return state.toString();
    }

    public  List<UUID> getPlayers() { return players; }

    public Game getGame() { return game;}

    public void setState(GameState state) {this.state = state;}

    /*-------------------------
               GAME
    -------------------------*/

    public void start () {
        game.start();
    }

    public void reset(boolean kickplayers) {
        if (kickplayers) {
            Location loc = ConfigManager.getLobbySpawn();
            for (UUID uuid : players) {
                Bukkit.getPlayer(uuid).teleport(loc);
            }
            players.clear();
        }
        sendTitle(" ", " ", 2, 2 , 2);
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(minigame, this);
        game = new Game(this);
    }

    /*-------------------------
               TOOLS
    -------------------------*/

    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }
    public void sendTitle(String title, String subtitle, int fadein, int stay, int fadeout) {
        //default 10(fadein), 70(stay), 20(fadeout) ticks
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle,fadein, stay, fadeout);
        }
    }
    public void sendSound(String sound, SoundCategory soundcategory, float volumen, float pitch) {
        for (UUID uuid : players) {
            Location loc1 = Bukkit.getPlayer(uuid).getLocation();
            Bukkit.getPlayer(uuid).getWorld().playSound(loc1, sound, soundcategory, volumen, pitch);
        }
    }
    public void sendMap(){
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).getInventory().addItem(new ItemStack(Material.MAP));
        }
    }
    public void sentActionBar(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendActionBar(message);
        }
    }

}
