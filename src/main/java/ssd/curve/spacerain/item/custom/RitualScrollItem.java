package ssd.curve.spacerain.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
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

    public void performRitual(Level level, BlockPos pos, Player player) {
        player.sendSystemMessage(Component.literal("developers please remember to like remove the super.performRitual thing i think"));
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
        Entity entity = EntityType.COW.create(level);
        entity.moveTo(pos, 0, 0);
        level.addFreshEntity(entity);
    }

    public List<Item> getItemRequirements() { // lmao get hardcoded nerd good luck kubejs people
        return List.of(Items.DIRT, Items.DIAMOND);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        List<Item> items = this.getItemRequirements();
        pTooltipComponents.add(Component.literal("§e" + Component.translatable("tooltip.scroll_info_text").getString()));

        for (Item reqitem: items) {
            String name = reqitem.getName(new ItemStack(reqitem)).getString();
            pTooltipComponents.add(Component.literal("§6" + name));

        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
