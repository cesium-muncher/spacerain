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

public class ScrollApplechantment extends RitualScrollItem {
    public ScrollApplechantment(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {
        ItemStack totem = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1);

        ItemStack catalyst = new ItemStack(ModItems.COBALT_CATALYST.get(), 1);
        if (player.getInventory().contains(catalyst)) {
            if (player.experienceLevel > 5 || player.isCreative()) {
                player.giveExperienceLevels(-5);
                player.drop(totem, false);
                player.hurt(level.damageSources().magic(), 2);
                ominoussfx(level, pos);
            } else {
                player.sendSystemMessage(Component.translatable("altar.spacerain.need_experience"));
                refundRitual(player);
            }

        } else {
            player.sendSystemMessage(Component.translatable("altar.spacerain.need_catalyst"));
            refundRitual(player);

        }

        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.GOLDEN_APPLE, ModItems.ASTRAL_JEWEL.get(), Items.LAPIS_LAZULI);
    }
}
