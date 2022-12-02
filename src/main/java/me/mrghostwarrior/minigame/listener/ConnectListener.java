package me.mrghostwarrior.minigame.listener;

import me.mrghostwarrior.minigame.GameState;
import me.mrghostwarrior.minigame.MiniGame;
import me.mrghostwarrior.minigame.instance.Arena;
import me.mrghostwarrior.minigame.manager.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private MiniGame minigame;

    public ConnectListener(MiniGame minigame){
        this.minigame = minigame;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().teleport(ConfigManager.getLobbySpawn());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Arena arena = minigame.getArenaManager().getArena(e.getPlayer());
        if (arena == null) { return; }
        arena.removePlayer(e.getPlayer());
    }
}
