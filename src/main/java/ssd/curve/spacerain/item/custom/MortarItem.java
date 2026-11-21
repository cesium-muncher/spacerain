package ssd.curve.spacerain.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ssd.curve.spacerain.item.ModItems;

public class MortarItem extends Item {

    public MortarItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(ModItems.MORTAR.get());
    }
}
