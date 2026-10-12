package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollWarp extends RitualScrollItem {
    public ScrollWarp(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {

        coolparticlefx((ServerLevel) level, player.blockPosition());
        player.hurt(level.damageSources().magic(), 1);

        player.teleportTo(pos.getX() + 0.5, pos.getY() + 1,pos.getZ() + 0.5);
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 40, 0));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0));


        //super.performRitual(level, pos, player);
    }
    private static void coolparticlefx(ServerLevel pLevel, BlockPos pPos) {
        for (int i=1; i<= 35; i++) {
            pLevel.sendParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    pPos.getX() + ((Math.random() - 0.5) * 2.0) + 0.5,
                    pPos.getY() + 1.0 + ((Math.random() - 0.5) * 1.0) + 0.5,
                    pPos.getZ() + ((Math.random() - 0.5) * 2.0) + 0.5,
                    10,
                    0,
                    0, 0, 0.05
            );




        }
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.ENDER_PEARL);
    }
}
