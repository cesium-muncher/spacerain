package ssd.curve.spacerain.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import ssd.curve.spacerain.Spacerain;

import java.util.List;

public class ModToolTiers {
    public static final Tier COBALT = TierSortingRegistry.registerTier(
            new ForgeTier(2, 300, 7.0f, 2.0f, 20,
                    BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ModItems.COBALT_INGOT.get())),
            new ResourceLocation(Spacerain.MODID, "cobalt"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND)


    );
    public static final Tier OMICRON = TierSortingRegistry.registerTier(
            new ForgeTier(4, 1000, 9.0f, 2.0f, 30,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(ModItems.COBALT_INGOT.get())),
            new ResourceLocation(Spacerain.MODID, "omicron"), List.of(Tiers.NETHERITE), List.of()
    );
}
