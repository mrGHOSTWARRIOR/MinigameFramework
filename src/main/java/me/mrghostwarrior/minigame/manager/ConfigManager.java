package me.mrghostwarrior.minigame.manager;

import me.mrghostwarrior.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(MiniGame defendTheFlag) {
        ConfigManager.config = defendTheFlag.getConfig();
        defendTheFlag.saveDefaultConfig();

    }

    public static int getRequiredPlayers(){ return config.getInt("required-players"); }
    public static int getMinimunPlayers() { return config.getInt("minimum-players"); }
    public static int getCountdownSeconds(){ return config.getInt("countdown-seconds"); }

    public static Location getLobbySpawn(){
        return new Location(
                Bukkit.getWorld("lobby-spawn.world"),
                config.getDouble("lobby-spawn.x"),
                config.getDouble("lobby-spawn.y"),
                config.getDouble("lobby-spawn.z"),
                (float) config.getDouble("lobby-spawn.yaw"),
                (float) config.getDouble("lobby-spawn.pitch"));
    }


}
