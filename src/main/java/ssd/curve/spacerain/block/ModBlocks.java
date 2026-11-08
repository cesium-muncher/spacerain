package ssd.curve.spacerain.block;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraftforge.registries.RegistryObject;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.block.custom.*;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.util.ModRarities;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Spacerain.MODID);

    public static final RegistryObject<Block> METEORITE_COBBLE = registerBlock("meteorite_cobble",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> METEORITE_BLOCK = registerBlock("meteorite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> MOLTEN_METEORITE_BLOCK = registerBlock("molten_meteorite_block",
            () -> new MoltenMeteorite(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.ORANGE).randomTicks()));
    public static final RegistryObject<Block> FRIGID_METEORITE_BLOCK = registerBlock("frigid_meteorite_block",
            () -> new FrigidMeteorite(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.LIGHT_BLUE).randomTicks()));

    public static final RegistryObject<Block> METEORIC_COPPER_ORE = registerBlock("meteoric_copper_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> METEORIC_IRON_ORE = registerBlock("meteoric_iron_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> METEORIC_GOLD_ORE = registerBlock("meteoric_gold_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> METEORIC_DIAMOND_ORE = registerBlock("meteoric_diamond_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));

    public static final RegistryObject<Block> METEORIC_LAPIS_ORE = registerBlock("meteoric_lapis_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> METEORIC_REDSTONE_ORE = registerBlock("meteoric_redstone_ore",
            () -> new RedStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> METEORIC_EMERALD_ORE = registerBlock("meteoric_emerald_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));

    public static final RegistryObject<Block> METEORIC_COBALTITE_ORE = registerBlock("meteoric_cobaltite_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> ASTRAL_JEWEL_ORE = registerBlock("astral_jewel_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> STELLAR_JEWEL_ORE = registerBlock("stellar_jewel_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));


    public static final RegistryObject<Block> ASTRAL_ALTAR = registerBlock("astral_altar",
            () -> new AstralAltarBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK).noOcclusion()));

    public static final RegistryObject<Block> STELLAR_BLIGHT = registerBlock("stellar_blight",
            () -> new BlightBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK).randomTicks()), ModRarities.RARITY_AWAKENED);
    public static final RegistryObject<Block> MGEBLOCK = registerBlock("meteor_spawner_block",
            () -> new MeteorSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).mapColor(DyeColor.BLACK)));
    public static final RegistryObject<Block> STAR = registerBlock("star",
            () -> new StarBlock(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE).mapColor(DyeColor.WHITE).noOcclusion()));

    public static final RegistryObject<Block> SOLARFIELD_1 = registerBlock("solarfield",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.GREEN)));
    public static final RegistryObject<Block> SOLARFIELD_2 = registerBlock("shimmering_solarfield",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.GREEN)));
    public static final RegistryObject<Block> SOLARFIELD_3 = registerBlock("glowing_solarfield",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.GREEN)));
    public static final RegistryObject<Block> SOLARFIELD_4 = registerBlock("blazing_solarfield",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.GREEN)));
    public static final RegistryObject<Block> SOLARFIELD_5 = registerBlock("astral_solarfield",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(DyeColor.GREEN)));
    public static final RegistryObject<Block> SOLARBED = registerBlock("solarbed",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).mapColor(DyeColor.BLACK)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Rarity r) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, r);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, Rarity r) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().rarity(r)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
