package me.mrghostwarrior.minigame.manager;

import me.mrghostwarrior.minigame.MiniGame;
import me.mrghostwarrior.minigame.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager(MiniGame miniGame){
        FileConfiguration config = miniGame.getConfig();

        for (String str : config.getConfigurationSection("arenas.").getKeys(false)) {
            arenas.add(new Arena(miniGame, Integer.parseInt(str),
                    new Location(
                            Bukkit.getWorld(config.getString("arenas." + str +".world")),
                            config.getDouble("arenas." + str +".x"),
                            config.getDouble("arenas." + str +".y"),
                            config.getDouble("arenas." + str +".z"),
                            (float) config.getDouble("arenas." + str +".yaw"),
                            (float) config.getDouble("arenas." + str +".pitch"))));
        }

    }

    public List<Arena> getArenas() { return arenas; }

    public Arena getArena(Player player) {
        for (Arena arena : arenas){
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return  arena;
            }
        }
        return null;
    }

    public Arena getArena(int id) {
        for (Arena arena : arenas){
            if (arena.getId() == id) {
                return  arena;
            }
        }
        return null;
    }

}
