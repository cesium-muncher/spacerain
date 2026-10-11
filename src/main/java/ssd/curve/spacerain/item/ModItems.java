package ssd.curve.spacerain.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.item.custom.OmicronItem;
import ssd.curve.spacerain.item.custom.RitualScrollItem;
import ssd.curve.spacerain.item.scrolls.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Spacerain.MODID);

    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot",
            () -> new Item(new Item.Properties()));



    public static final RegistryObject<Item> COBALT_SWORD = ITEMS.register("cobalt_sword",
            () -> new SwordItem(ModToolTiers.COBALT, 3, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
            () -> new PickaxeItem(ModToolTiers.COBALT, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_AXE = ITEMS.register("cobalt_axe",
            () -> new AxeItem(ModToolTiers.COBALT, 6, -3.1f, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_SHOVEL = ITEMS.register("cobalt_shovel",
            () -> new ShovelItem(ModToolTiers.COBALT, 1.5f, -3, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_HOE = ITEMS.register("cobalt_hoe",
            () -> new HoeItem(ModToolTiers.COBALT, -2, -1, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_OMICRON = ITEMS.register("cobalt_omicron",
            () -> new OmicronItem(ModToolTiers.COBALT, 5, -2f, new Item.Properties()));
    public static final RegistryObject<Item> AWAKENED_COBALT_OMICRON = ITEMS.register("awakened_cobalt_omicron",
            () -> new OmicronItem(ModToolTiers.OMICRON, 7, -1.8f, new Item.Properties()));
    public static final RegistryObject<Item> STELLAR_COBALT_OMICRON = ITEMS.register("stellar_cobalt_omicron",
            () -> new OmicronItem(ModToolTiers.OMICRON, 9, -1.6f, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_HELMET = ITEMS.register("cobalt_helmet",
            () -> new ArmorItem(ModArmorMaterials.COBALT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_CHESTPLATE = ITEMS.register("cobalt_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COBALT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_LEGGINGS = ITEMS.register("cobalt_leggings",
            () -> new ArmorItem(ModArmorMaterials.COBALT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_BOOTS = ITEMS.register("cobalt_boots",
            () -> new ArmorItem(ModArmorMaterials.COBALT, ArmorItem.Type.BOOTS, new Item.Properties()));


    public static final RegistryObject<Item> COBALT_CATALYST = ITEMS.register("cobalt_catalyst",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FALLEN_SIGIL = ITEMS.register("fallen_sigil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AWAKENED_SIGIL = ITEMS.register("awakened_sigil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ASTRAL_JEWEL = ITEMS.register("astral_jewel",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DEBUG_SCROLL = ITEMS.register("debug_scroll",
            () -> new RitualScrollItem(new Item.Properties()));
    public static final RegistryObject<Item> SCROLL_TRANS_IRON = ITEMS.register("scroll_transmutate_iron",
            () -> new ScrollTransmutateIron(new Item.Properties()));
    public static final RegistryObject<Item> SCROLL_OMICRON = ITEMS.register("scroll_omicron",
            () -> new ScrollOmicronCreation(new Item.Properties()));
    public static final RegistryObject<Item> SCROLL_TRUE_LIGHT = ITEMS.register("scroll_true_light",
            () -> new ScrollGoodEffects(new Item.Properties()));
    public static final RegistryObject<Item> SCROLL_RESIST = ITEMS.register("scroll_extended_endurance",
            () -> new ScrollLongResist(new Item.Properties()));
    public static final RegistryObject<Item> SCROLL_REGEN = ITEMS.register("scroll_extended_healing",
            () -> new ScrollLongRegen(new Item.Properties()));
    public static final RegistryObject<Item> SCROLL_FOOD = ITEMS.register("scroll_saturate",
            () -> new ScrollFood(new Item.Properties()));
    public static final RegistryObject<Item> SCROLL_EVIL_CURSE = ITEMS.register("scroll_evil_curse",
            () -> new ScrollEvilCurse(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
