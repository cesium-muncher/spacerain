package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollFood extends RitualScrollItem {
    public ScrollFood(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {


        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20,  15));


        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ROTTEN_FLESH);
    }
}
