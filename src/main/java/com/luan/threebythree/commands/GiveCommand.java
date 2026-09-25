package com.luan.threebythree.commands;

import com.luan.threebythree.ThreeByThree;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GiveCommand implements CommandExecutor, TabCompleter {

    private final ThreeByThree plugin;
    private static final List<String> TYPES = Arrays.asList("pickaxe", "shovel", "axe", "hoe");
    private static final List<String> TIERS = Arrays.asList("wood", "stone", "copper", "iron", "diamond");

    public GiveCommand(ThreeByThree plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("threebythree.give")) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUsage: /give3x3 <player> <type> [tier]");
            sender.sendMessage("§cTypes: pickaxe, shovel, axe, hoe");
            sender.sendMessage("§cTiers: wood, stone, copper, iron, diamond (default: diamond)");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }

        String type = args[1].toLowerCase(Locale.ROOT);
        if (!TYPES.contains(type)) {
            sender.sendMessage("§cInvalid type. Use: pickaxe, shovel, axe, hoe");
            return true;
        }

        String tier = args.length >= 3 ? args[2].toLowerCase(Locale.ROOT) : "diamond";
        
        if (tier.equals("madeira")) tier = "wood";
        else if (tier.equals("pedra")) tier = "stone";
        else if (tier.equals("cobre")) tier = "copper";
        else if (tier.equals("ferro")) tier = "iron";
        else if (tier.equals("diamante")) tier = "diamond";

        ItemStack item = plugin.getToolManager().createTool(type, tier);
        if (item == null) {
            sender.sendMessage("§cInvalid tier. Use: wood, stone, copper, iron, diamond");
            return true;
        }

        target.getInventory().addItem(item);
        sender.sendMessage("§aGave 3x3 " + type + " (" + tier + ") to " + target.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("threebythree.give")) {
            return Collections.emptyList();
        }

        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String prefix = args[0].toLowerCase(Locale.ROOT);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase(Locale.ROOT).startsWith(prefix)) {
                    completions.add(p.getName());
                }
            }
        } else if (args.length == 2) {
            String prefix = args[1].toLowerCase(Locale.ROOT);
            for (String t : TYPES) {
                if (t.startsWith(prefix)) {
                    completions.add(t);
                }
            }
        } else if (args.length == 3) {
            String prefix = args[2].toLowerCase(Locale.ROOT);
            for (String tr : TIERS) {
                if (tr.startsWith(prefix)) {
                    completions.add(tr);
                }
            }
        }

        return completions;
    }
}
