package com.luan.threebythree.items;

import com.luan.threebythree.ThreeByThree;
import com.luan.threebythree.utils.LegacyUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ToolManager {

    private final ThreeByThree plugin;
    public static final String LORE_IDENTIFIER = "§7Area 3x3";

    public ToolManager(ThreeByThree plugin) {
        this.plugin = plugin;
    }

    public ItemStack createTool(String param1, String param2) {
        if (isKnownTier(param2) && isKnownType(param1)) {
            return createToolByTier(param1, param2);
        }
        return createCustomTool(param1, param2);
    }

    public ItemStack createCustomTool(String materialName, String displayName) {
        Material mat = LegacyUtils.getMaterial(materialName);
        if (mat == null) {
            plugin.getLogger().warning("Invalid material: " + materialName);
            return null;
        }
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        List<String> lore = new ArrayList<>();
        lore.add(LORE_IDENTIFIER);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createToolByTier(String type, String tier) {
        String copperBase = plugin.getConfig().getString("copper_base_material", "IRON");
        Material mat = LegacyUtils.getToolMaterial(type, tier, copperBase);
        if (mat == null) {
            plugin.getLogger().warning("Invalid tool material for type: " + type + ", tier: " + tier);
            return null;
        }

        String displayName = getDisplayName(type, tier);
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        List<String> lore = new ArrayList<>();
        lore.add(LORE_IDENTIFIER);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private boolean isKnownType(String type) {
        if (type == null) return false;
        String p = type.toLowerCase(Locale.ROOT);
        return p.equals("pickaxe") || p.equals("shovel") || p.equals("axe") || p.equals("hoe");
    }

    private boolean isKnownTier(String tier) {
        if (tier == null) return false;
        String t = tier.toLowerCase(Locale.ROOT);
        return t.equals("wood") || t.equals("madeira") || t.equals("stone") || t.equals("pedra")
                || t.equals("copper") || t.equals("cobre") || t.equals("iron") || t.equals("ferro")
                || t.equals("diamond") || t.equals("diamante");
    }

    public String getDisplayName(String type, String tier) {
        String t = tier.toLowerCase();
        String p = type.toLowerCase();

        String toolNamePt;
        switch (p) {
            case "pickaxe": toolNamePt = "Picareta"; break;
            case "shovel":  toolNamePt = "Pá"; break;
            case "axe":     toolNamePt = "Machado"; break;
            case "hoe":     toolNamePt = "Enxada"; break;
            default:        toolNamePt = "Ferramenta"; break;
        }

        if (t.equals("wood") || t.equals("madeira")) {
            return "&eSuper " + toolNamePt + " de Madeira 3x3";
        } else if (t.equals("stone") || t.equals("pedra")) {
            return "&7Super " + toolNamePt + " de Pedra 3x3";
        } else if (t.equals("copper") || t.equals("cobre")) {
            return "&6Super " + toolNamePt + " de Cobre 3x3";
        } else if (t.equals("iron") || t.equals("ferro")) {
            return "&fSuper " + toolNamePt + " de Ferro 3x3";
        } else {
            return "&bSuper " + toolNamePt + " de Diamante 3x3";
        }
    }

    public boolean isThreeByThreeTool(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore())
            return false;
        return item.getItemMeta().getLore().contains(LORE_IDENTIFIER);
    }

    public void registerRecipes() {
        FileConfiguration config = plugin.getConfig();

        String[] toolTypes = { "pickaxe", "shovel", "axe", "hoe" };
        String[] tiers = { "wood", "stone", "copper", "iron", "diamond" };

        Material stickMat = LegacyUtils.getMaterial("STICK");
        if (stickMat == null) {
            stickMat = Material.matchMaterial("STICK");
        }

        for (String type : toolTypes) {
            if (!config.getBoolean("crafting." + type, true)) {
                continue;
            }

            for (String tier : tiers) {
                if (!config.getBoolean("crafting.tiers." + tier, true)) {
                    continue;
                }

                List<Material> primaryIngredients = getPrimaryIngredients(tier);
                if (primaryIngredients.isEmpty() || stickMat == null) {
                    continue;
                }

                ItemStack result = createTool(type, tier);
                if (result == null) {
                    continue;
                }

                registerToolRecipes(type, tier, result, primaryIngredients, stickMat);
            }
        }
    }

    private List<Material> getPrimaryIngredients(String tier) {
        String t = tier.toLowerCase();
        if (t.equals("wood") || t.equals("madeira")) {
            return LegacyUtils.getRawWoodMaterials();
        } else if (t.equals("stone") || t.equals("pedra")) {
            return LegacyUtils.getSmoothStoneMaterials();
        } else if (t.equals("copper") || t.equals("cobre")) {
            return LegacyUtils.getCopperBlockMaterials();
        } else if (t.equals("iron") || t.equals("ferro")) {
            Material ironBlock = LegacyUtils.getMaterial("IRON_BLOCK");
            return ironBlock != null ? Collections.singletonList(ironBlock) : Collections.emptyList();
        } else {
            Material diamondBlock = LegacyUtils.getMaterial("DIAMOND_BLOCK");
            return diamondBlock != null ? Collections.singletonList(diamondBlock) : Collections.emptyList();
        }
    }

    private void registerToolRecipes(String type, String tier, ItemStack result,
                                     List<Material> primaryIngs, Material stickMat) {
        switch (type.toLowerCase()) {
            case "pickaxe":
                
                registerRecipeWithIngredients(type + "_" + tier + "_std", result,
                        new String[] { "III", " S ", " S " }, primaryIngs, stickMat);
                
                registerRecipeWithIngredients(type + "_" + tier + "_alt", result,
                        new String[] { "III", " I ", " S " }, primaryIngs, stickMat);
                break;
            case "shovel":
                
                registerRecipeWithIngredients(type + "_" + tier, result,
                        new String[] { "I", "S", "S" }, primaryIngs, stickMat);
                break;
            case "axe":
                
                registerRecipeWithIngredients(type + "_" + tier + "_left", result,
                        new String[] { "II", "IS", " S" }, primaryIngs, stickMat);
                
                registerRecipeWithIngredients(type + "_" + tier + "_right", result,
                        new String[] { "II", "SI", "S " }, primaryIngs, stickMat);
                break;
            case "hoe":
                
                registerRecipeWithIngredients(type + "_" + tier + "_left", result,
                        new String[] { "II", " S", " S" }, primaryIngs, stickMat);
                
                registerRecipeWithIngredients(type + "_" + tier + "_right", result,
                        new String[] { "II", "S ", "S " }, primaryIngs, stickMat);
                break;
        }
    }

    private void registerRecipeWithIngredients(String keySuffix, ItemStack result, String[] shape,
                                               List<Material> primaryIngs, Material stickMat) {
        try {
            boolean hasRecipeChoice = true;
            try {
                Class.forName("org.bukkit.inventory.RecipeChoice");
            } catch (ClassNotFoundException e) {
                hasRecipeChoice = false;
            }

            if (!hasRecipeChoice) {
                registerLegacyShapedRecipe(result, shape, primaryIngs.get(0), stickMat);
                return;
            }

            String keyStr = ("tbt_" + keySuffix).toLowerCase(Locale.ROOT);
            NamespacedKey key = new NamespacedKey(plugin, keyStr);

            try {
                if (Bukkit.getRecipe(key) != null) {
                    Bukkit.removeRecipe(key);
                }
            } catch (Throwable ignored) {
            }

            ShapedRecipe recipe = new ShapedRecipe(key, result);
            recipe.shape(shape);
            recipe.setIngredient('S', stickMat);

            if (primaryIngs.size() == 1) {
                recipe.setIngredient('I', primaryIngs.get(0));
            } else {
                recipe.setIngredient('I', new RecipeChoice.MaterialChoice(primaryIngs));
            }

            Bukkit.addRecipe(recipe);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to register recipe for key: " + keySuffix);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void registerLegacyShapedRecipe(ItemStack result, String[] shape, Material primaryMat, Material stickMat) {
        try {
            ShapedRecipe recipe = new ShapedRecipe(result);
            recipe.shape(shape);
            recipe.setIngredient('I', primaryMat);
            recipe.setIngredient('S', stickMat);
            Bukkit.addRecipe(recipe);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to register legacy recipe");
            e.printStackTrace();
        }
    }
}
