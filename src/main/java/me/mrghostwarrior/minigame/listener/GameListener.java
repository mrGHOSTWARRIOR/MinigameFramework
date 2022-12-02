package me.mrghostwarrior.minigame.listener;

import me.mrghostwarrior.minigame.GameState;
import me.mrghostwarrior.minigame.MiniGame;
import me.mrghostwarrior.minigame.instance.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameListener implements Listener {

    private MiniGame minigame;

    public GameListener(MiniGame minigame){
        this.minigame = minigame;
    }

    //example
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Arena arena = minigame.getArenaManager().getArena(e.getPlayer());
        if (arena == null || arena.getState() != GameState.LIVE) { return; }
        arena.getGame().addPoint(e.getPlayer());
    }

}
