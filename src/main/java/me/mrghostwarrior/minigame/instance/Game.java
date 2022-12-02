package me.mrghostwarrior.minigame.instance;

import me.mrghostwarrior.minigame.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena) {
        this.arena = arena;
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendTitle(
                ChatColor.translateAlternateColorCodes('&',
                        "&2&lGAME"),
                ChatColor.translateAlternateColorCodes('&',
                        "&f&lhas started!"),
                10, 20, 5);

        for (UUID uuid : arena.getPlayers()){
            //run for everyplayer
        }
    }

    public void addPoint(Player player) {
        int playerPoints = points.get(player.getUniqueId()) + 1;
        if (playerPoints == 20) {
            arena.sendMessage(ChatColor.GOLD + player.getName() + " HAS WON!");
            arena.reset(true);
            return;
        }

        player.sendMessage(ChatColor.RED + "+1 POINT!");
        points.replace(player.getUniqueId(), playerPoints);
    }

}
