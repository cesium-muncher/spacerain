package ssd.curve.spacerain.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RitualScrollItem extends Item {
    public RitualScrollItem(Properties pProperties) {
        super(pProperties);
    }

    public void performRitual(Level Level, BlockPos Pos, Player player) {
        player.sendSystemMessage(Component.literal("how did you get this scroll"));
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
    }

    public List<Item> getItemRequirements() { // lmao get hardcoded nerd good luck kubejs people
        return List.of(Items.DIRT, Items.DIAMOND);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        List<Item> items = this.getItemRequirements();
        pTooltipComponents.add(Component.translatable("tooltip.scroll_info_text"));

        for (Item reqitem: items) {
            String name = reqitem.getName(new ItemStack(reqitem)).getString();
            pTooltipComponents.add(Component.literal("§9" + name));

        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
