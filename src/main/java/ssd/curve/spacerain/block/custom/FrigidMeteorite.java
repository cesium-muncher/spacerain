package ssd.curve.spacerain.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import ssd.curve.spacerain.block.ModBlocks;

public class FrigidMeteorite extends MagmaBlock {

    public FrigidMeteorite(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (!pEntity.isSteppingCarefully() && pEntity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)pEntity)) {
            //pEntity.hurt(pLevel.damageSources().freeze(), 1.0F);
            pEntity.setIsInPowderSnow(true);
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }


    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {

        pLevel.playSound(null, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
        pLevel.sendParticles(ParticleTypes.CLOUD, pPos.getX(), pPos.getY(), pPos.getZ(), 5, 0.125, 0.125, 0.125, 0.2);
        pLevel.setBlockAndUpdate(pPos, ModBlocks.METEORITE_BLOCK.get().defaultBlockState());


        //super.randomTick(ModBlocks.METEORITE_BLOCK.get().defaultBlockState(), pLevel, pPos, pRandom);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        player.extinguishFire();
        player.hurt(level.damageSources().freeze(), 3.0F);

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
