package me.mrghostwarrior.minigame.instance;

import me.mrghostwarrior.minigame.GameState;
import me.mrghostwarrior.minigame.MiniGame;
import me.mrghostwarrior.minigame.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.SoundCategory;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private MiniGame miniGame;
    private Arena arena;
    private int countdownSeconds;

    public Countdown(MiniGame miniGame, Arena arena){
        this.miniGame = miniGame;
        this.arena = arena;
        this.countdownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start() {
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(miniGame, 0, 20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0) {
            cancel();
            arena.start();
            return;
        }
        //sending messages
        if (countdownSeconds <= 10 || countdownSeconds % 15 == 0) {
            arena.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                    "&2&lGame starting in &f&l" + countdownSeconds + "&2&l&second" + (countdownSeconds == 1 ? "" : "s" + ".")));
        }
        //sending title
        if (countdownSeconds <= 5) {
            arena.sendTitle(
                    ChatColor.translateAlternateColorCodes('&',
                    "&2&lGAME"),
                    ChatColor.translateAlternateColorCodes('&',
                    "&2&lstarting in &fl&" + countdownSeconds + "&2&l&second" + (countdownSeconds == 1 ? "" : "s" + ".")),
                    10, 70, 20);
            countdownSeconds--;
        }
        //sending sounds
        if (countdownSeconds == 30 || countdownSeconds == 10 || countdownSeconds <= 5){
            arena.sendSound("minecraft:entity.player.levelup",
                    SoundCategory.PLAYERS, 1, 1);
        }
    }
}
