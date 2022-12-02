package me.mrghostwarrior.minigame;

import me.mrghostwarrior.minigame.command.ArenaCommand;
import me.mrghostwarrior.minigame.listener.ConnectListener;
import me.mrghostwarrior.minigame.listener.GameListener;
import me.mrghostwarrior.minigame.manager.ArenaManager;
import me.mrghostwarrior.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiniGame extends JavaPlugin {


    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        //files
        ConfigManager.setupConfig(this);
        //manager
        arenaManager = new ArenaManager(this);
        //command
        getCommand("arena").setExecutor(new ArenaCommand(this));
        //listener
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
    }

    public ArenaManager getArenaManager() { return  arenaManager; }


}
