package me.mrghostwarrior.minigame.listener;

import me.mrghostwarrior.minigame.MiniGame;
import me.mrghostwarrior.minigame.instance.Arena;
import me.mrghostwarrior.minigame.manager.ConfigManager;
import org.bukkit.entity.Player;
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
        Player player = e.getPlayer();
        player.teleport(ConfigManager.getLobbySpawn());
        e.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Arena arena = minigame.getArenaManager().getArena(e.getPlayer());
        if (arena == null) { return; }
        arena.removePlayer(e.getPlayer());
    }
}
