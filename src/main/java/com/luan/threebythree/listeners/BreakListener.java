package com.luan.threebythree.listeners;

import com.luan.threebythree.ThreeByThree;
import com.luan.threebythree.utils.LegacyUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BreakListener implements Listener {

    private final ThreeByThree plugin;
    private final List<String> processedBlocks = new ArrayList<>();

    public BreakListener(ThreeByThree plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        ItemStack item;
        try {
            item = player.getInventory().getItemInMainHand();
        } catch (NoSuchMethodError e) {
            item = player.getItemInHand();
        }

        if (!plugin.getToolManager().isThreeByThreeTool(item))
            return;

        Block centerBlock = event.getBlock();

        if (processedBlocks.contains(locKey(centerBlock))) {
            processedBlocks.remove(locKey(centerBlock));
            return;
        }

        BlockFace face = getTargetBlockFace(player);
        if (face == null)
            return;

        List<Block> blocksToBreak = getSurroundingBlocks(centerBlock, face);

        for (Block b : blocksToBreak) {
            if (b.getType() == Material.AIR)
                continue;
            if (!isValidTarget(item.getType(), b.getType()))
                continue;

            processedBlocks.add(locKey(b));

            BlockBreakEvent newEvent = new BlockBreakEvent(b, player);
            plugin.getServer().getPluginManager().callEvent(newEvent);

            if (!newEvent.isCancelled()) {
                b.breakNaturally(item);

                int exp = newEvent.getExpToDrop();
                if (exp > 0) {
                    b.getWorld().spawn(b.getLocation(), org.bukkit.entity.ExperienceOrb.class).setExperience(exp);
                }

                if (item.getType().getMaxDurability() > 0) {
                    int unbreakingLevel = item.getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DURABILITY);
                    double chance = 1.0 / (unbreakingLevel + 1);

                    if (Math.random() < chance) {
                        item.setDurability((short) (item.getDurability() + 1));

                        if (item.getDurability() > item.getType().getMaxDurability()) {
                            try {
                                player.getInventory().setItemInMainHand(null);
                            } catch (NoSuchMethodError e) {
                                player.setItemInHand(null);
                            }
                            try {
                                player.playSound(player.getLocation(), org.bukkit.Sound.valueOf("ENTITY_ITEM_BREAK"), 1,
                                        1);
                            } catch (IllegalArgumentException e) {
                                try {
                                    player.playSound(player.getLocation(), org.bukkit.Sound.valueOf("ITEM_BREAK"), 1,
                                            1);
                                } catch (Exception ignored) {
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private String locKey(Block b) {
        return b.getWorld().getName() + "," + b.getX() + "," + b.getY() + "," + b.getZ();
    }

    public boolean isValidTarget(Material tool, Material block) {
        if (block == null || block == Material.AIR) {
            return false;
        }

        String blockName = block.name();

        if (blockName.equals("BEDROCK") || blockName.equals("BARRIER")
                || blockName.startsWith("COMMAND_BLOCK") || blockName.endsWith("COMMAND_BLOCK")
                || blockName.contains("PORTAL") || blockName.equals("STRUCTURE_BLOCK")
                || blockName.equals("JIGSAW")) {
            return false;
        }

        if (plugin != null && plugin.getConfig() != null) {
            List<String> blacklist = plugin.getConfig().getStringList("blacklist");
            if (blacklist != null && blacklist.contains(blockName)) {
                return false;
            }
        }

        if (LegacyUtils.isPickaxe(tool)) {
            try {
                if (org.bukkit.Tag.MINEABLE_PICKAXE.isTagged(block)) {
                    return true;
                }
            } catch (Throwable ignored) {
            }

            return blockName.contains("STONE") || blockName.contains("COBBLE") || blockName.contains("DEEPSLATE")
                    || blockName.contains("TUFF") || blockName.contains("CALCITE") || blockName.contains("ANDESITE")
                    || blockName.contains("DIORITE") || blockName.contains("GRANITE") || blockName.contains("BASALT")
                    || blockName.contains("TERRACOTTA") || blockName.contains("CONCRETE") || blockName.endsWith("BRICK")
                    || blockName.contains("BRICKS") || blockName.contains("PRISMARINE") || blockName.contains("PURPUR")
                    || blockName.contains("OBSIDIAN") || blockName.contains("AMETHYST") || blockName.contains("QUARTZ")
                    || blockName.contains("NETHERRACK") || blockName.contains("MAGMA") || blockName.contains("ICE")
                    || blockName.contains("ORE") || blockName.contains("DEBRIS") || blockName.contains("ANVIL")
                    || blockName.contains("CAULDRON") || blockName.contains("HOPPER") || blockName.contains("SPAWNER")
                    || blockName.contains("RAIL") || blockName.contains("LANTERN") || blockName.contains("CHAIN")
                    || blockName.endsWith("_BLOCK") || blockName.startsWith("RAW_") || blockName.contains("COPPER")
                    || blockName.contains("IRON") || blockName.contains("GOLD") || blockName.contains("DIAMOND")
                    || blockName.contains("EMERALD") || blockName.contains("LAPIS") || blockName.contains("REDSTONE")
                    || blockName.contains("FURNACE") || blockName.contains("SMOKER") || blockName.contains("STONECUTTER");
        }

        if (LegacyUtils.isShovel(tool)) {
            try {
                if (org.bukkit.Tag.MINEABLE_SHOVEL.isTagged(block)) {
                    return true;
                }
            } catch (Throwable ignored) {
            }

            return blockName.contains("DIRT") || blockName.contains("SAND") || blockName.contains("GRAVEL")
                    || blockName.contains("GRASS") || blockName.contains("SOUL") || blockName.contains("CLAY")
                    || blockName.contains("MUD") || blockName.contains("SNOW") || blockName.contains("POWDER")
                    || blockName.contains("PODZOL") || blockName.contains("MYCELIUM") || blockName.contains("PATH")
                    || blockName.contains("FARMLAND");
        }

        if (LegacyUtils.isAxe(tool)) {
            try {
                if (org.bukkit.Tag.MINEABLE_AXE.isTagged(block)) {
                    return true;
                }
            } catch (Throwable ignored) {
            }

            return blockName.contains("LOG") || blockName.contains("WOOD") || blockName.contains("PLANKS")
                    || blockName.contains("STEM") || blockName.contains("HYPHAE") || blockName.contains("BAMBOO")
                    || blockName.contains("MUSHROOM") || blockName.contains("CHEST") || blockName.contains("BARREL")
                    || blockName.contains("TABLE") || blockName.contains("BOOKSHELF") || blockName.contains("PUMPKIN")
                    || blockName.contains("MELON") || blockName.contains("COMPOSTER") || blockName.contains("BEE")
                    || blockName.contains("FENCE") || blockName.contains("GATE") || blockName.contains("DOOR")
                    || blockName.contains("TRAPDOOR") || blockName.contains("SIGN") || blockName.contains("CAMPFIRE")
                    || blockName.contains("LADDER");
        }

        if (LegacyUtils.isHoe(tool)) {
            try {
                if (org.bukkit.Tag.MINEABLE_HOE.isTagged(block)) {
                    return true;
                }
            } catch (Throwable ignored) {
            }

            return blockName.contains("LEAVES") || blockName.contains("WART") || blockName.contains("SHROOMLIGHT")
                    || blockName.contains("HAY") || blockName.contains("TARGET") || blockName.contains("SPONGE")
                    || blockName.contains("SCULK") || blockName.contains("MOSS") || blockName.contains("KELP");
        }

        return true;
    }

    private List<Block> getSurroundingBlocks(Block center, BlockFace face) {
        List<Block> blocks = new ArrayList<>();

        int xMod = 0;
        int yMod = 0;
        int zMod = 0;

        if (face == BlockFace.UP || face == BlockFace.DOWN) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && z == 0)
                        continue;
                    blocks.add(center.getRelative(x, 0, z));
                }
            }
        } else if (face == BlockFace.NORTH || face == BlockFace.SOUTH) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0)
                        continue;
                    blocks.add(center.getRelative(x, y, 0));
                }
            }
        } else {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {
                    if (z == 0 && y == 0)
                        continue;
                    blocks.add(center.getRelative(0, y, z));
                }
            }
        }
        return blocks;
    }

    private BlockFace getTargetBlockFace(Player player) {
        float pitch = player.getLocation().getPitch();
        float yaw = player.getLocation().getYaw();

        if (pitch > 50)
            return BlockFace.DOWN;
        if (pitch < -50)
            return BlockFace.UP;

        yaw = (yaw % 360 + 360) % 360;

        if (yaw > 315 || yaw <= 45)
            return BlockFace.SOUTH;
        if (yaw > 45 && yaw <= 135)
            return BlockFace.WEST;
        if (yaw > 135 && yaw <= 225)
            return BlockFace.NORTH;
        if (yaw > 225 && yaw <= 315)
            return BlockFace.EAST;

        return BlockFace.NORTH;
    }
}
