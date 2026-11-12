package ssd.curve.spacerain.block.entity;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Spacerain.MODID);


    public static final RegistryObject<BlockEntityType<StarBlockEntity>> STAR_BLOCK_BE =
            BLOCK_ENTITIES.register("star_block_be", () ->
                    BlockEntityType.Builder.of(StarBlockEntity::new,
                            ModBlocks.STAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ScribingTableBlockEntity>> SCRIBING_TABLE_BE =
            BLOCK_ENTITIES.register("scribing_table_be", () ->
                    BlockEntityType.Builder.of(ScribingTableBlockEntity::new,
                            ModBlocks.SCRIBING_TABLE.get()).build(null));

    public static void register(IEventBus eventbus) {
        BLOCK_ENTITIES.register(eventbus);
    }
}
