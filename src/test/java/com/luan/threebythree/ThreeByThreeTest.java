package com.luan.threebythree;

import com.luan.threebythree.listeners.BreakListener;
import com.luan.threebythree.utils.LegacyUtils;
import org.bukkit.Material;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ThreeByThreeTest {

    @Test
    public void testToolClassification() {
        assertTrue(LegacyUtils.isPickaxe(Material.DIAMOND_PICKAXE));
        assertTrue(LegacyUtils.isPickaxe(Material.IRON_PICKAXE));
        assertTrue(LegacyUtils.isPickaxe(Material.STONE_PICKAXE));
        assertTrue(LegacyUtils.isPickaxe(Material.WOODEN_PICKAXE));

        assertTrue(LegacyUtils.isShovel(Material.DIAMOND_SHOVEL));
        assertTrue(LegacyUtils.isShovel(Material.IRON_SHOVEL));

        assertTrue(LegacyUtils.isAxe(Material.DIAMOND_AXE));
        assertTrue(LegacyUtils.isAxe(Material.IRON_AXE));

        assertTrue(LegacyUtils.isHoe(Material.DIAMOND_HOE));
        assertTrue(LegacyUtils.isHoe(Material.IRON_HOE));

        assertFalse(LegacyUtils.isPickaxe(Material.DIAMOND_SWORD));
        assertFalse(LegacyUtils.isPickaxe(null));
    }

    @Test
    public void testToolMaterialsByTier() {
        assertEquals(Material.WOODEN_PICKAXE, LegacyUtils.getToolMaterial("pickaxe", "wood", "IRON"));
        assertEquals(Material.STONE_PICKAXE, LegacyUtils.getToolMaterial("pickaxe", "stone", "IRON"));
        assertEquals(Material.IRON_PICKAXE, LegacyUtils.getToolMaterial("pickaxe", "copper", "IRON"));
        assertEquals(Material.GOLDEN_PICKAXE, LegacyUtils.getToolMaterial("pickaxe", "copper", "GOLDEN"));
        assertEquals(Material.IRON_PICKAXE, LegacyUtils.getToolMaterial("pickaxe", "iron", "IRON"));
        assertEquals(Material.DIAMOND_PICKAXE, LegacyUtils.getToolMaterial("pickaxe", "diamond", "IRON"));

        assertEquals(Material.WOODEN_SHOVEL, LegacyUtils.getToolMaterial("shovel", "madeira", "IRON"));
        assertEquals(Material.STONE_AXE, LegacyUtils.getToolMaterial("axe", "pedra", "IRON"));
        assertEquals(Material.IRON_HOE, LegacyUtils.getToolMaterial("hoe", "ferro", "IRON"));
        assertEquals(Material.DIAMOND_PICKAXE, LegacyUtils.getToolMaterial("pickaxe", "diamante", "IRON"));
    }

    @Test
    public void testRawWoodIngredients() {
        List<Material> rawWood = LegacyUtils.getRawWoodMaterials();
        assertNotNull(rawWood);
        assertFalse(rawWood.isEmpty());
        assertTrue(rawWood.contains(Material.OAK_LOG));
        assertTrue(rawWood.contains(Material.BIRCH_LOG));
        assertTrue(rawWood.contains(Material.SPRUCE_LOG));
        
        for (Material m : rawWood) {
            assertFalse(m.name().startsWith("STRIPPED_"));
        }
    }

    @Test
    public void testSmoothStoneAndCopperBlocks() {
        List<Material> smoothStone = LegacyUtils.getSmoothStoneMaterials();
        assertNotNull(smoothStone);
        assertFalse(smoothStone.isEmpty());
        assertTrue(smoothStone.contains(Material.SMOOTH_STONE));

        List<Material> copperBlocks = LegacyUtils.getCopperBlockMaterials();
        assertNotNull(copperBlocks);
        assertFalse(copperBlocks.isEmpty());
        assertTrue(copperBlocks.contains(Material.COPPER_BLOCK));
    }

    @Test
    public void testBreakListenerPickaxeValidTargets() {
        BreakListener listener = new BreakListener(null);
        Material pick = Material.DIAMOND_PICKAXE;

        assertTrue(listener.isValidTarget(pick, Material.STONE));
        assertTrue(listener.isValidTarget(pick, Material.COBBLESTONE));
        assertTrue(listener.isValidTarget(pick, Material.DEEPSLATE));
        assertTrue(listener.isValidTarget(pick, Material.COBBLED_DEEPSLATE));
        assertTrue(listener.isValidTarget(pick, Material.TUFF));
        assertTrue(listener.isValidTarget(pick, Material.CALCITE));
        assertTrue(listener.isValidTarget(pick, Material.ANDESITE));
        assertTrue(listener.isValidTarget(pick, Material.DIORITE));
        assertTrue(listener.isValidTarget(pick, Material.GRANITE));
        assertTrue(listener.isValidTarget(pick, Material.BASALT));
        assertTrue(listener.isValidTarget(pick, Material.POLISHED_BASALT));
        assertTrue(listener.isValidTarget(pick, Material.BLACKSTONE));
        assertTrue(listener.isValidTarget(pick, Material.SANDSTONE));
        assertTrue(listener.isValidTarget(pick, Material.RED_SANDSTONE));
        assertTrue(listener.isValidTarget(pick, Material.END_STONE));
        assertTrue(listener.isValidTarget(pick, Material.TERRACOTTA));
        assertTrue(listener.isValidTarget(pick, Material.WHITE_CONCRETE));
        assertTrue(listener.isValidTarget(pick, Material.OBSIDIAN));
        assertTrue(listener.isValidTarget(pick, Material.AMETHYST_BLOCK));
        assertTrue(listener.isValidTarget(pick, Material.PRISMARINE));
        assertTrue(listener.isValidTarget(pick, Material.PURPUR_BLOCK));
        assertTrue(listener.isValidTarget(pick, Material.IRON_ORE));
        assertTrue(listener.isValidTarget(pick, Material.DEEPSLATE_DIAMOND_ORE));
        assertTrue(listener.isValidTarget(pick, Material.NETHERRACK));
        assertTrue(listener.isValidTarget(pick, Material.IRON_BLOCK));
        assertTrue(listener.isValidTarget(pick, Material.COPPER_BLOCK));
        assertTrue(listener.isValidTarget(pick, Material.DIAMOND_BLOCK));

        assertFalse(listener.isValidTarget(pick, Material.BEDROCK));
        assertFalse(listener.isValidTarget(pick, Material.BARRIER));
        assertFalse(listener.isValidTarget(pick, Material.COMMAND_BLOCK));
        assertFalse(listener.isValidTarget(pick, Material.CHAIN_COMMAND_BLOCK));
        assertFalse(listener.isValidTarget(pick, Material.REPEATING_COMMAND_BLOCK));
        assertFalse(listener.isValidTarget(pick, Material.NETHER_PORTAL));
        assertFalse(listener.isValidTarget(pick, Material.END_PORTAL));
        assertFalse(listener.isValidTarget(pick, Material.AIR));
        assertFalse(listener.isValidTarget(pick, null));
    }

    @Test
    public void testBreakListenerShovelTargets() {
        BreakListener listener = new BreakListener(null);
        Material shovel = Material.DIAMOND_SHOVEL;

        assertTrue(listener.isValidTarget(shovel, Material.DIRT));
        assertTrue(listener.isValidTarget(shovel, Material.GRASS_BLOCK));
        assertTrue(listener.isValidTarget(shovel, Material.SAND));
        assertTrue(listener.isValidTarget(shovel, Material.GRAVEL));
        assertTrue(listener.isValidTarget(shovel, Material.CLAY));
        assertTrue(listener.isValidTarget(shovel, Material.MUD));
        assertTrue(listener.isValidTarget(shovel, Material.SNOW_BLOCK));
        assertTrue(listener.isValidTarget(shovel, Material.WHITE_CONCRETE_POWDER));
        assertTrue(listener.isValidTarget(shovel, Material.SOUL_SAND));
        assertTrue(listener.isValidTarget(shovel, Material.SOUL_SOIL));
    }

    @Test
    public void testBreakListenerAxeTargets() {
        BreakListener listener = new BreakListener(null);
        Material axe = Material.DIAMOND_AXE;

        assertTrue(listener.isValidTarget(axe, Material.OAK_LOG));
        assertTrue(listener.isValidTarget(axe, Material.OAK_WOOD));
        assertTrue(listener.isValidTarget(axe, Material.OAK_PLANKS));
        assertTrue(listener.isValidTarget(axe, Material.CHEST));
        assertTrue(listener.isValidTarget(axe, Material.BARREL));
        assertTrue(listener.isValidTarget(axe, Material.CRAFTING_TABLE));
        assertTrue(listener.isValidTarget(axe, Material.BOOKSHELF));
        assertTrue(listener.isValidTarget(axe, Material.PUMPKIN));
        assertTrue(listener.isValidTarget(axe, Material.MELON));
    }

    @Test
    public void testBreakListenerHoeTargets() {
        BreakListener listener = new BreakListener(null);
        Material hoe = Material.DIAMOND_HOE;

        assertTrue(listener.isValidTarget(hoe, Material.OAK_LEAVES));
        assertTrue(listener.isValidTarget(hoe, Material.NETHER_WART_BLOCK));
        assertTrue(listener.isValidTarget(hoe, Material.WARPED_WART_BLOCK));
        assertTrue(listener.isValidTarget(hoe, Material.SHROOMLIGHT));
        assertTrue(listener.isValidTarget(hoe, Material.HAY_BLOCK));
        assertTrue(listener.isValidTarget(hoe, Material.TARGET));
        assertTrue(listener.isValidTarget(hoe, Material.SPONGE));
        assertTrue(listener.isValidTarget(hoe, Material.MOSS_BLOCK));
        assertTrue(listener.isValidTarget(hoe, Material.SCULK));
    }
}
