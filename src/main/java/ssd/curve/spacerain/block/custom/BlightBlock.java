package ssd.curve.spacerain.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fml.common.Mod;
import ssd.curve.spacerain.block.ModBlocks;

public class BlightBlock extends Block {

    public BlightBlock(Properties pProperties) {
        super(pProperties);
    }

    private void blight(ServerLevel pLevel, BlockPos pPos) {
        BlockState bs = pLevel.getBlockState(pPos);
        if (bs.is(ModBlocks.ASTRAL_JEWEL_ORE.get())) {
            // sigil
        } else if (bs.is(ModBlocks.METEORITE_BLOCK.get()) || !bs.isSolid()) {
            // nothing happens
        } else if (pLevel.getBiome(pPos).is(BiomeTags.IS_OCEAN) || pLevel.getBiome(pPos).is(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)) {
            pLevel.setBlockAndUpdate(pPos, ModBlocks.METEORITE_BLOCK.get().defaultBlockState());
        } else if (bs.isSolid() && !bs.is(ModBlocks.METEORITE_BLOCK.get()) && !bs.is(ModBlocks.ASTRAL_JEWEL_ORE.get())) {
            pLevel.setBlockAndUpdate(pPos, ModBlocks.STELLAR_BLIGHT.get().defaultBlockState());
        }
    }


    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {

        pLevel.playSound(null, pPos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS);


        //pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
        pLevel.setBlockAndUpdate(pPos, ModBlocks.METEORITE_BLOCK.get().defaultBlockState());

        blight(pLevel, pPos.below());
        blight(pLevel, pPos.above());
        blight(pLevel, pPos.north());
        blight(pLevel, pPos.east());
        blight(pLevel, pPos.south());
        blight(pLevel, pPos.west());


        //super.randomTick(ModBlocks.METEORITE_BLOCK.get().defaultBlockState(), pLevel, pPos, pRandom);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (!pLevel.isClientSide) {
            if (LivingEntity.class.isAssignableFrom(pEntity.getClass())) {
                LivingEntity pEntity2 = (LivingEntity) pEntity;
                pEntity2.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3600, 3));
                //pEntity2.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 3600, 0));
                pEntity2.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 3600, 0));
                pEntity2.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 2));
                pEntity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 1));
                pEntity2.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 3600, 0));
                pEntity2.addEffect(new MobEffectInstance(MobEffects.WITHER, 3600, 0));
            }
        }

        //super.stepOn(pLevel, pPos, pState, pEntity);
    }

}
