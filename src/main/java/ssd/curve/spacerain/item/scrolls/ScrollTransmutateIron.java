package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollTransmutateIron extends RitualScrollItem {
    public ScrollTransmutateIron(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {
        ItemStack cobalt = new ItemStack(ModItems.COBALT_INGOT.get(), 1);
        ItemStack iron = new ItemStack(Items.IRON_INGOT, 1);
        ItemStack catalyst = new ItemStack(ModItems.COBALT_CATALYST.get(), 1);
        if (player.getInventory().contains(catalyst)) {
            player.drop(cobalt, false);
        } else {
            player.sendSystemMessage(Component.translatable("altar.spacerain.need_catalyst"));
            player.drop(iron, false);
        }

        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.IRON_INGOT);
    }
}
