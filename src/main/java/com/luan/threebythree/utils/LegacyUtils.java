package com.luan.threebythree.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LegacyUtils {

    private static final Map<String, Material> CACHE = new HashMap<>();

    public static Material getMaterial(String name) {
        if (CACHE.containsKey(name))
            return CACHE.get(name);

        Material material = Material.getMaterial(name);
        if (material != null) {
            CACHE.put(name, material);
            return material;
        }

        switch (name) {
            case "WOODEN_PICKAXE":
                return getMaterial("WOOD_PICKAXE");
            case "WOOD_PICKAXE":
                return getMaterial("WOODEN_PICKAXE");
            case "GOLDEN_PICKAXE":
                return getMaterial("GOLD_PICKAXE");
            case "GOLD_PICKAXE":
                return getMaterial("GOLDEN_PICKAXE");
            case "STONE_PICKAXE":
                return Material.matchMaterial("STONE_PICKAXE");
            case "IRON_PICKAXE":
                return Material.matchMaterial("IRON_PICKAXE");
            case "DIAMOND_PICKAXE":
                return Material.matchMaterial("DIAMOND_PICKAXE");
            case "NETHERITE_PICKAXE":
                return Material.matchMaterial("NETHERITE_PICKAXE");

            case "WOODEN_SHOVEL":
                return getMaterial("WOOD_SPADE");
            case "WOOD_SPADE":
                return getMaterial("WOODEN_SHOVEL");
            case "STONE_SHOVEL":
                return getMaterial("STONE_SPADE");
            case "STONE_SPADE":
                return getMaterial("STONE_SHOVEL");
            case "GOLDEN_SHOVEL":
                return getMaterial("GOLD_SPADE");
            case "GOLD_SPADE":
                return getMaterial("GOLDEN_SHOVEL");
            case "IRON_SHOVEL":
                return getMaterial("IRON_SPADE");
            case "IRON_SPADE":
                return getMaterial("IRON_SHOVEL");
            case "DIAMOND_SHOVEL":
                return getMaterial("DIAMOND_SPADE");
            case "DIAMOND_SPADE":
                return getMaterial("DIAMOND_SHOVEL");
            case "NETHERITE_SHOVEL":
                return Material.matchMaterial("NETHERITE_SHOVEL");

            case "WOODEN_AXE":
                return getMaterial("WOOD_AXE");
            case "WOOD_AXE":
                return getMaterial("WOODEN_AXE");
            case "GOLDEN_AXE":
                return getMaterial("GOLD_AXE");
            case "GOLD_AXE":
                return getMaterial("GOLDEN_AXE");

            case "WOODEN_HOE":
                return getMaterial("WOOD_HOE");
            case "WOOD_HOE":
                return getMaterial("WOODEN_HOE");
            case "GOLDEN_HOE":
                return getMaterial("GOLD_HOE");
            case "GOLD_HOE":
                return getMaterial("GOLDEN_HOE");
        }

        Material match = Material.matchMaterial(name);
        if (match != null) {
            CACHE.put(name, match);
            return match;
        }

        return null;
    }

    public static Material getToolMaterial(String type, String tier, String copperBase) {
        String t = tier.toLowerCase();
        String p = type.toLowerCase();

        if (t.equals("wood") || t.equals("madeira")) {
            switch (p) {
                case "pickaxe": return getMaterial("WOODEN_PICKAXE");
                case "shovel":  return getMaterial("WOODEN_SHOVEL");
                case "axe":     return getMaterial("WOODEN_AXE");
                case "hoe":     return getMaterial("WOODEN_HOE");
            }
        } else if (t.equals("stone") || t.equals("pedra")) {
            switch (p) {
                case "pickaxe": return getMaterial("STONE_PICKAXE");
                case "shovel":  return getMaterial("STONE_SHOVEL");
                case "axe":     return getMaterial("STONE_AXE");
                case "hoe":     return getMaterial("STONE_HOE");
            }
        } else if (t.equals("copper") || t.equals("cobre")) {
            Material nativeTool = getMaterial("COPPER_" + p.toUpperCase());
            if (nativeTool != null) {
                return nativeTool;
            }
            boolean useGolden = copperBase != null && copperBase.equalsIgnoreCase("GOLDEN");
            if (useGolden) {
                switch (p) {
                    case "pickaxe": return getMaterial("GOLDEN_PICKAXE");
                    case "shovel":  return getMaterial("GOLDEN_SHOVEL");
                    case "axe":     return getMaterial("GOLDEN_AXE");
                    case "hoe":     return getMaterial("GOLDEN_HOE");
                }
            } else {
                switch (p) {
                    case "pickaxe": return getMaterial("IRON_PICKAXE");
                    case "shovel":  return getMaterial("IRON_SHOVEL");
                    case "axe":     return getMaterial("IRON_AXE");
                    case "hoe":     return getMaterial("IRON_HOE");
                }
            }
        } else if (t.equals("iron") || t.equals("ferro")) {
            switch (p) {
                case "pickaxe": return getMaterial("IRON_PICKAXE");
                case "shovel":  return getMaterial("IRON_SHOVEL");
                case "axe":     return getMaterial("IRON_AXE");
                case "hoe":     return getMaterial("IRON_HOE");
            }
        } else {
            
            switch (p) {
                case "pickaxe": return getMaterial("DIAMOND_PICKAXE");
                case "shovel":  return getMaterial("DIAMOND_SHOVEL");
                case "axe":     return getMaterial("DIAMOND_AXE");
                case "hoe":     return getMaterial("DIAMOND_HOE");
            }
        }
        return null;
    }

    public static java.util.List<Material> getRawWoodMaterials() {
        java.util.List<Material> list = new java.util.ArrayList<>();
        for (Material m : Material.values()) {
            String name = m.name();
            if ((name.endsWith("_LOG") || name.endsWith("_WOOD") || name.endsWith("_STEM") || name.endsWith("_HYPHAE"))
                    && !name.startsWith("STRIPPED_") && !name.startsWith("LEGACY_")) {
                list.add(m);
            }
        }
        if (list.isEmpty()) {
            Material legacyLog = getMaterial("LOG");
            if (legacyLog != null) list.add(legacyLog);
            Material legacyLog2 = getMaterial("LOG_2");
            if (legacyLog2 != null) list.add(legacyLog2);
            Material oakLog = getMaterial("OAK_LOG");
            if (oakLog != null && !list.contains(oakLog)) list.add(oakLog);
        }
        return list;
    }

    public static java.util.List<Material> getSmoothStoneMaterials() {
        java.util.List<Material> list = new java.util.ArrayList<>();
        Material smoothStone = getMaterial("SMOOTH_STONE");
        if (smoothStone != null) {
            list.add(smoothStone);
        } else {
            Material stone = getMaterial("STONE");
            if (stone != null) list.add(stone);
        }
        return list;
    }

    public static java.util.List<Material> getCopperBlockMaterials() {
        java.util.List<Material> list = new java.util.ArrayList<>();
        String[] candidates = { "COPPER_BLOCK", "CUT_COPPER", "WAXED_COPPER_BLOCK", "WAXED_CUT_COPPER" };
        for (String c : candidates) {
            Material m = getMaterial(c);
            if (m != null) list.add(m);
        }
        if (list.isEmpty()) {
            Material ironBlock = getMaterial("IRON_BLOCK");
            if (ironBlock != null) list.add(ironBlock);
        }
        return list;
    }

    public static boolean isPickaxe(Material m) {
        if (m == null) return false;
        String name = m.name();
        return name.endsWith("_PICKAXE");
    }

    public static boolean isShovel(Material m) {
        if (m == null) return false;
        String name = m.name();
        return name.endsWith("_SPADE") || name.endsWith("_SHOVEL");
    }

    public static boolean isAxe(Material m) {
        if (m == null) return false;
        String name = m.name();
        return name.endsWith("_AXE");
    }

    public static boolean isHoe(Material m) {
        if (m == null) return false;
        String name = m.name();
        return name.endsWith("_HOE");
    }
}
