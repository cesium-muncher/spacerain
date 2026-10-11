package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollGoodEffects extends RitualScrollItem {
    public ScrollGoodEffects(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {


        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 72000,  3));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 72000,  1));
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 72000,  0));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600,  5));


        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.GOLDEN_APPLE, Items.BLAZE_POWDER, ModItems.FALLEN_SIGIL.get(), Items.DIAMOND, ModItems.ASTRAL_JEWEL.get(), Items.FERMENTED_SPIDER_EYE);
    }
}
