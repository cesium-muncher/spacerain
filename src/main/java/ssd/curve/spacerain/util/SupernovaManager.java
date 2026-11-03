package ssd.curve.spacerain.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.common.Mod;
import ssd.curve.spacerain.block.ModBlocks;

public class SupernovaManager {
    private static final BlockState solarbed = ModBlocks.SOLARBED.get().defaultBlockState();
    private static final BlockState solarfield1 = ModBlocks.SOLARFIELD_1.get().defaultBlockState();
    private static final BlockState solarfield2 = ModBlocks.SOLARFIELD_2.get().defaultBlockState();
    private static final BlockState solarfield3 = ModBlocks.SOLARFIELD_3.get().defaultBlockState();
    private static final BlockState solarfield4 = ModBlocks.SOLARFIELD_4.get().defaultBlockState();
    private static final BlockState solarfield5 = ModBlocks.SOLARFIELD_5.get().defaultBlockState();

    private static final TagKey<Block> tag_dirt = BlockTags.DIRT;
    private static final TagKey<Block> tag_stone = BlockTags.STONE_ORE_REPLACEABLES;
    private static final TagKey<Block> tag_deep = BlockTags.DEEPSLATE_ORE_REPLACEABLES;
    private static final TagKey<Block> tag_ore = Tags.Blocks.ORES;
    private static final TagKey<Block> tag_repl = BlockTags.REPLACEABLE;
    private static final TagKey<Block> tag_tree = BlockTags.COMPLETES_FIND_TREE_TUTORIAL;
    public static void SupernovaAtBlockPos (Level pLevel, BlockPos pos, double dist, double hdist, ImprovedNoise noise, double radius) {
        BlockState blockstate = pLevel.getBlockState(pos);
        Block block = blockstate.getBlock();
        float blastres = block.getExplosionResistance();
        double blastnoise = noise.noise(pos.getX() * 0.05, 0.0,pos.getZ() * 0.05) * 5;
        float radius2 = (float) (radius + blastnoise);
        // ok so uhhh
        // blast FORCE number...
        // starts at 10
        // decreases by -1/50(dist-50) + random
        // if blast res is less than uhhh
        // blast force then desintegrate
        // blast conversion number: uhhh maybe exactly the same but +-5?
        // if not desintegrated then convert to new block

        // -10/(max-min) * (d-min) + 10
        //float blastforce = (float) ((-10.0/maxdist-mindist) * (dist - mindist) + blastnoise + 10.0);
        //float blastconvnum = blastforce + 250.0f;

        // stone/deepslate is 6
        // meteoric blocks is 9
        // dirt is 0.5


        if (blastres > 3000000f) {
            pLevel.setBlockAndUpdate(pos, solarbed);
        } else {

            if (radius2 > 2*dist) {
                pLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            } else if (radius2 > dist) {
                if (blockstate.is(tag_repl) || blockstate.is(tag_tree)) {
                    pLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                } else {
                    if (hdist < radius * 0.2) {
                        pLevel.setBlockAndUpdate(pos, solarfield5);
                    } else if (hdist < radius * 0.4) {
                        pLevel.setBlockAndUpdate(pos, solarfield4);
                    } else if (hdist < radius * 0.6) {
                        pLevel.setBlockAndUpdate(pos, solarfield3);
                    } else if (hdist < radius * 0.8) {
                        pLevel.setBlockAndUpdate(pos, solarfield2);
                    } else if (hdist < radius * 1) {
                        pLevel.setBlockAndUpdate(pos, solarfield1);
                    }

                }
            }




        }

    }
    //private static double heightmult = 1;
    public static void SupernovaTest(Level pLevel, BlockPos pos, int radius) {
        RandomSource rs = RandomSource.create((long) (Math.random()*100000));
        ImprovedNoise noise = new ImprovedNoise(rs);
        for (int x = -radius; x <= radius; x++) {
            for (int y = (int) -(radius); y <= (int) (radius); y++) {
                for (int z = -radius; z <= radius; z++) {
                    double dist = Math.sqrt(x*x + (y*y*(1.0) + (radius/2.0)) + z*z);
                    double hdist = Math.sqrt(x*x + z*z) + (Math.random() * 8.0) - 4.0;
                    SupernovaAtBlockPos(pLevel, pos.offset(x, y, z), dist, hdist, noise, radius);
                }
            }
        }
    }
    public static void SupernovaGenVericalColumn(Level pLevel, BlockPos center, int radius, BlockPos columnloc, ImprovedNoise noise) {
        for (int y = (int) -(radius); y <= (int) (radius); y++) {
            int x = columnloc.getX() - center.getX();
            int z = columnloc.getZ() - center.getZ(); // is this right??

            double dist = Math.sqrt(x*x + (y*y*(1.0) + (radius/2.0)) + z*z);
            double hdist = Math.sqrt(x*x + z*z) + (Math.random() * 8.0) - 4.0;
            SupernovaAtBlockPos(pLevel, center.offset(x, y, z), dist, hdist, noise, radius);
        }
    }

    public static void SupernovaGenSquareLayer(Level pLevel, BlockPos center, int radius, int currentradius, ImprovedNoise noise) {
        // currentradius uhhh
        // ummm
        // looping uhh....
        // -cr, -cr, -cr -> -cr, cr, cr (x static, y and z change) "-x"
        // cr, -cr, -cr -> cr, cr, cr, (x static, y and z change) "+x"
        // -cr+1, -cr, -cr -> cr-1, cr, -cr (z static, x and y change, x shrunk by 1) "-z"
        // -cr+1, -cr, cr -> cr-1, cr, cr (z static, x and y change, x shrunk by 1) "+z"

        // ignore Y for now, use the uhhh column function


        // -x
        for (int z = -currentradius; z <= currentradius; z++) {
            SupernovaGenVericalColumn(pLevel, center, radius, center.offset(-currentradius, 0, z), noise);
        }

        // +x
        for (int z = -currentradius; z <= currentradius; z++) {
            SupernovaGenVericalColumn(pLevel, center, radius, center.offset(currentradius, 0, z), noise);
        }

        // -z
        for (int x = -currentradius + 1; x <= currentradius - 1; x++) {
            SupernovaGenVericalColumn(pLevel, center, radius, center.offset(x, 0, -currentradius), noise);
        }

        // +z
        for (int x = -currentradius + 1; x <= currentradius - 1; x++) {
            SupernovaGenVericalColumn(pLevel, center, radius, center.offset(x, 0, currentradius), noise);
        }



    }

}
