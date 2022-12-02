package me.mrghostwarrior.minigame.command;

import me.mrghostwarrior.minigame.GameState;
import me.mrghostwarrior.minigame.MiniGame;
import me.mrghostwarrior.minigame.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArenaCommand implements CommandExecutor {

    private MiniGame minigame;

    public ArenaCommand(MiniGame minigame){
        this.minigame = minigame;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&2These are the available arenas:"));
                for (Arena arena : minigame.getArenaManager().getArenas()){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&2&l-&2") + arena.getId() + " " + arena.getStateName());
                }
            }
            else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                Arena arena = minigame.getArenaManager().getArena(player);
                if (arena != null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&c&lYou left the arena!"));
                    arena.removePlayer(player);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&c&lYou are not in an arena!"));
                }
            }
            else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                Arena arena = minigame.getArenaManager().getArena(player);
                if (arena != null){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&c&lYou are already in an arena!"));
                    return false;
                }
                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch (NumberFormatException e ){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&c&lYou specified an invalid arena ID!"));
                    return false;
                }
                if (id >= 0 && id < minigame.getArenaManager().getArenas().size()){
                    if (arena.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN ){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&c&lYou are now playing in arena " + id + "."));
                        arena.addPlayer(player);
                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&c&lYou cannot join arena while is in " + arena.getStateName()));
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&c&lYou specified an invalid arena ID!"));
                }
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&cInvalid usage! these are the options:"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&e /arena list"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&e /arena leave"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&e /arena join <id>"));
            }
        }
     return false;
    }
}
