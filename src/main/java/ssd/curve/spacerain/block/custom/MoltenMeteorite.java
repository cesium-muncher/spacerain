package ssd.curve.spacerain.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import ssd.curve.spacerain.block.ModBlocks;

public class MoltenMeteorite extends MagmaBlock {

    public MoltenMeteorite(Properties pProperties) {
        super(pProperties);
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
        player.setSecondsOnFire(5);

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
