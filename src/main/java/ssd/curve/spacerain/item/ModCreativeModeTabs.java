package ssd.curve.spacerain.item;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Spacerain.MODID);

    public static final RegistryObject<CreativeModeTab> SPACERAIN_TAB = CREATIVE_MODE_TABS.register("spacerain_tab",
    () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.METEORITE_COBBLE.get()))
            .title(Component.translatable("creativetab.spacerain_tab"))
            .displayItems((ItemDisplayParameters, output) -> {

                output.accept(ModItems.COBALT_INGOT.get());
                output.accept(ModItems.COBALT_SWORD.get());
                output.accept(ModItems.COBALT_PICKAXE.get());
                output.accept(ModItems.COBALT_AXE.get());
                output.accept(ModItems.COBALT_SHOVEL.get());
                output.accept(ModItems.COBALT_HOE.get());
                output.accept(ModItems.COBALT_HELMET.get());
                output.accept(ModItems.COBALT_CHESTPLATE.get());
                output.accept(ModItems.COBALT_LEGGINGS.get());
                output.accept(ModItems.COBALT_BOOTS.get());

                output.accept(ModBlocks.METEORIC_COBALTITE_ORE.get());
                output.accept(ModBlocks.ASTRAL_JEWEL_ORE.get());
                output.accept(ModBlocks.METEORITE_COBBLE.get());
                output.accept(ModBlocks.METEORITE_BLOCK.get());
                output.accept(ModBlocks.MOLTEN_METEORITE_BLOCK.get());
                output.accept(ModBlocks.FRIGID_METEORITE_BLOCK.get());
                output.accept(ModBlocks.METEORIC_COPPER_ORE.get());
                output.accept(ModBlocks.METEORIC_IRON_ORE.get());
                output.accept(ModBlocks.METEORIC_GOLD_ORE.get());
                output.accept(ModBlocks.METEORIC_DIAMOND_ORE.get());
                output.accept(ModBlocks.METEORIC_EMERALD_ORE.get());
                output.accept(ModBlocks.METEORIC_REDSTONE_ORE.get());
                output.accept(ModBlocks.METEORIC_LAPIS_ORE.get());
                output.accept(ModBlocks.ASTRAL_ALTAR.get());
                output.accept(ModItems.COBALT_CATALYST.get());
                output.accept(ModItems.FALLEN_SIGIL.get());
                output.accept(ModItems.AWAKENED_SIGIL.get());
                output.accept(ModItems.ASTRAL_JEWEL.get());
                output.accept(ModItems.COBALT_OMICRON.get());
                output.accept(ModItems.AWAKENED_COBALT_OMICRON.get());

            })
            .build());
    public static final RegistryObject<CreativeModeTab> SPACERAIN_SCROLLS_TAB = CREATIVE_MODE_TABS.register("spacerain_scrolls_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.DEBUG_SCROLL.get()))
                    .title(Component.translatable("creativetab.spacerain_scrolls_tab"))
                    .displayItems((ItemDisplayParameters, output) -> {
                        output.accept(ModItems.DEBUG_SCROLL.get());
                    })
                    .build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
