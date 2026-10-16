package ssd.curve.spacerain.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import ssd.curve.spacerain.block.ModBlocks;
import ssd.curve.spacerain.util.MeteorGenerator;

public class MeteorSpawnerBlock extends Block {

    public MeteorSpawnerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        MeteorGenerator mg = new MeteorGenerator();
        mg.generateAny(pLevel, pPos);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.scheduleTick(pPos, this, 20);
        //MeteorGenerator mg = new MeteorGenerator();
        //mg.generateAny(pLevel, pPos);
    }
}
