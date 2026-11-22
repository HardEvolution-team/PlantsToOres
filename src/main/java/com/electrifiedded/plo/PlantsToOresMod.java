package com.electrifiedded.plo;

import com.electrifiedded.archaiclib.original.OriginalModAssets;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:archaiclib@[1.25.0,)")
@Mod.EventBusSubscriber
public class PlantsToOresMod {
    public static final String ORIGINAL_NAME = "Plants_To_Ores_V1.0.jar - Plants To Ores 1.0 for 1.7.10";
    public static final String ORIGINAL_URL = "https://www.curseforge.com/minecraft/mc-mods/plants-to-ores";

    static final String[] NAMES = {"lapis", "coal", "diamond", "emerald", "gold", "iron", "redstone"};
    static final Block[] ORES = {Blocks.LAPIS_ORE, Blocks.COAL_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE,
        Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.REDSTONE_ORE};
    static final Block[] CENTERS = {Blocks.LAPIS_BLOCK, Blocks.COAL_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.EMERALD_BLOCK,
        Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.REDSTONE_BLOCK};
    /** stem tint as in the original */
    static final int[] COLORS = {0x3300FF, 0x000000, 0x00FFFF, 0x00FF00, 0xFFFF00, 0xCCCCCC, 0xFF3300};

    static final PlantStem[] STEMS = new PlantStem[NAMES.length];
    static final Item[] SEEDS = new Item[NAMES.length];

    public static final CreativeTabs TAB = new CreativeTabs("tabPlant_To_Ores") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(SEEDS[2]);
        }
    };

    static {
        for (int i = 0; i < NAMES.length; i++) {
            String n = NAMES[i];
            PlantStem s = new PlantStem(ORES[i], COLORS[i]);
            s.setRegistryName(Reference.MOD_ID, n + "_ore_stem");
            s.setTranslationKey("Block" + cap(n) + "PlantStem");
            ItemSeeds seeds = new ItemSeeds(s, Blocks.FARMLAND);
            seeds.setRegistryName(Reference.MOD_ID, n + "_ore_seeds");
            seeds.setTranslationKey("Block" + cap(n) + "PlantSeeds");
            seeds.setCreativeTab(TAB);
            s.seeds = seeds;
            STEMS[i] = s;
            SEEDS[i] = seeds;
        }
    }

    static String cap(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        // textures come from the original 1.7.10 jar (client side only)
        OriginalModAssets.require(Reference.MOD_ID, Reference.MOD_ID, ORIGINAL_NAME, ORIGINAL_URL, Reference.MOD_ID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        for (int i = 0; i < NAMES.length; i++) {
            GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID, NAMES[i] + "_ore_seeds"), null,
                new ItemStack(SEEDS[i]), "SSS", "SBS", "SSS",
                'S', Items.WHEAT_SEEDS, 'B', new ItemStack(CENTERS[i]));
        }
    }

    @SubscribeEvent
    public static void blocks(RegistryEvent.Register<Block> e) {
        e.getRegistry().registerAll(STEMS);
    }

    @SubscribeEvent
    public static void items(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(SEEDS);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void models(ModelRegistryEvent e) {
        for (int i = 0; i < NAMES.length; i++) {
            ModelLoader.setCustomModelResourceLocation(SEEDS[i], 0,
                new ModelResourceLocation(SEEDS[i].getRegistryName(), "inventory"));
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void colors(ColorHandlerEvent.Block e) {
        for (int i = 0; i < NAMES.length; i++) {
            final int color = STEMS[i].color;
            e.getBlockColors().registerBlockColorHandler((state, w, pos, tint) -> color, STEMS[i]);
        }
    }
}
