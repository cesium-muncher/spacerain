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

public class ScrollTransmutateObsidian extends RitualScrollItem {
    public ScrollTransmutateObsidian(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {
        ItemStack refdeep = new ItemStack(Items.REINFORCED_DEEPSLATE, 1);
        ItemStack catalyst = new ItemStack(ModItems.COBALT_CATALYST.get(), 1);
        if (player.getInventory().contains(catalyst)) {
            player.drop(refdeep, false);
            player.hurt(level.damageSources().magic(), 2);
            ominoussfx(level, pos);
        } else {
            player.sendSystemMessage(Component.translatable("altar.spacerain.need_catalyst"));
            refundRitual(player);
        }

        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.OBSIDIAN, ModItems.FALLEN_SIGIL.get(), Items.DEEPSLATE_BRICKS, Items.BONE_BLOCK, ModItems.COBALT_INGOT.get());
    }
}
