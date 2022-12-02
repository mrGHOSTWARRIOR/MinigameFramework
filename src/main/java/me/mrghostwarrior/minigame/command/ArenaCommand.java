package me.mrghostwarrior.minigame.command;

import me.mrghostwarrior.minigame.GameState;
import me.mrghostwarrior.minigame.MiniGame;

import me.mrghostwarrior.minigame.instance.Arena;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
                    if (arena.getState() == GameState.LIVE){
                        TextComponent component = new TextComponent("§2§l- §f§l" +arena.getId() + " " + arena.getstateNameForComponentText());
                        player.spigot().sendMessage(component);
                    }else {
                        TextComponent component = new TextComponent("§2§l- §f§l" +arena.getId() + " " + arena.getstateNameForComponentText());
                        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arena join " + arena.getId()));
                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§f§l--------------\n  §e§lClick to join\n§f§l--------------")));
                        player.spigot().sendMessage(component);
                    }
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
                if (minigame.getArenaManager().getArena(player) != null){
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
                    Arena arena = minigame.getArenaManager().getArena(id);
                    if (arena.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN ){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&2&lYou are now playing in arena &e&l" + id + "&2&l."));
                        arena.addPlayer(player);
                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&c&lYou cannot join arena while is in " +  arena.getStateName()));
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
