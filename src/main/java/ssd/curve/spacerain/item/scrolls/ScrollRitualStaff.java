package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollRitualStaff extends RitualScrollItem {
    public ScrollRitualStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {
        ItemStack cobalt = new ItemStack(ModItems.RITUAL_STAFF.get(), 1);


        player.drop(cobalt, false);


        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(ModItems.REFINED_INK.get(), ModItems.COBALT_INGOT.get(), ModItems.SILICON.get(), ModItems.CARBON.get(), ModItems.PARCHMENT.get(), ModItems.COBALT_CATALYST.get());
    }
}
