package ssd.curve.spacerain.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.ChunkEvent;
import org.jetbrains.annotations.NotNull;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.core.internal.world.VsiPlayer;
import org.valkyrienskies.mod.api.ValkyrienSkies;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;
import org.valkyrienskies.mod.common.assembly.ShipAssembler;
import org.valkyrienskies.mod.mixinducks.world.entity.PlayerDuck;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.block.ModBlocks;
import ssd.curve.spacerain.sound.ModSounds;

import java.util.*;

public class MeteorGenerator {

    public void generateAny(Level level, BlockPos pos) {
        // meteor 90%, comet 9.9%, jackpot 0.1%
        /*
        meteor weights:
        small 5
        med 7
        largepoor 3
        largerich 1

        comet weights:
        small 8
        medium 5
        mediumpoor 7
        lpicy 3
        largerich 2
         */
        int rand = (int) (Math.random() * 1000) + 1;
        if (rand <= 900) {
            generateMeteor(level, pos);
        } else if (rand <= 999) {
            generateComet(level, pos);
        } else {
            prefab_jackpot(level, pos);
        }
    }
    public static void generateLayer0(Level level, BlockPos pos, Block block) {
        level.setBlockAndUpdate(pos, block.defaultBlockState());
    }
    public static void generateSolidLayerAuto5(Level level, BlockPos pos, Block block, int mindist, int maxdist) {
        int range = 5;
        for (int x=-range; x<=range; x++) {
            for (int y=-range; y<=range; y++) {
                for (int z=-range; z<=range; z++) {
                    double dist = Math.floor(Math.sqrt(x*x + y*y + z*z));
                    if (dist >= mindist && dist <= maxdist) {
                        BlockPos newbps = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        level.setBlockAndUpdate(newbps, block.defaultBlockState());
                    }
                }
            }
        }
    }
    public static void generateLayerAuto5_ListRandom(Level level, BlockPos pos, List<Block> blocklist, int mindist, int maxdist) {
        int range = 5;
        for (int x=-range; x<=range; x++) {
            for (int y=-range; y<=range; y++) {
                for (int z=-range; z<=range; z++) {
                    double dist = Math.floor(Math.sqrt(x*x + y*y + z*z));
                    if (dist >= mindist && dist <= maxdist) {
                        BlockPos newbps = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        Block block = blocklist.get((int) (Math.random() * blocklist.size()));
                        level.setBlockAndUpdate(newbps, block.defaultBlockState());
                    }
                }
            }
        }
    }

    public static List<Block> genericmeteorshell() {
        return List.of(
                ModBlocks.METEORITE_COBBLE.get(),
                ModBlocks.METEORIC_GOLD_ORE.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get(),
                ModBlocks.MOLTEN_METEORITE_BLOCK.get());
    } // i put molten thrice because like idk

    public static List<Block> genericcometshell() {
        return List.of(

                ModBlocks.METEORIC_COBALTITE_ORE.get(),
                ModBlocks.METEORITE_COBBLE.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get(),
                ModBlocks.FRIGID_METEORITE_BLOCK.get());
    }
    public static List<Block> genericoresbasic() {
        return List.of(ModBlocks.METEORIC_COPPER_ORE.get(), ModBlocks.METEORIC_IRON_ORE.get(), ModBlocks.METEORIC_GOLD_ORE.get(), ModBlocks.METEORIC_LAPIS_ORE.get(), ModBlocks.METEORIC_REDSTONE_ORE.get(), ModBlocks.METEORIC_COBALTITE_ORE.get());
    }
    public static List<Block> genericoresvaluable() {
        return List.of(ModBlocks.METEORIC_DIAMOND_ORE.get(), ModBlocks.METEORIC_COBALTITE_ORE.get(), ModBlocks.METEORIC_EMERALD_ORE.get());
    }
    public static List<Block> cometoresouter() {
        return List.of(ModBlocks.METEORIC_DIAMOND_ORE.get(), ModBlocks.ASTRAL_JEWEL_ORE.get(), ModBlocks.METEORIC_COBALTITE_ORE.get(), ModBlocks.METEORIC_COBALTITE_ORE.get(), ModBlocks.METEORIC_COBALTITE_ORE.get(), ModBlocks.METEORIC_COBALTITE_ORE.get());
    }
    public static List<Block> cometoresinner() {
        return List.of(ModBlocks.METEORIC_COBALTITE_ORE.get(), ModBlocks.ASTRAL_JEWEL_ORE.get(), ModBlocks.ASTRAL_JEWEL_ORE.get(), ModBlocks.ASTRAL_JEWEL_ORE.get());
    }
    public static List<Block> cometice() {
        return List.of(Blocks.BLUE_ICE, Blocks.PACKED_ICE, Blocks.PACKED_ICE, Blocks.PACKED_ICE, Blocks.PACKED_ICE, Blocks.PACKED_ICE, Blocks.ICE, Blocks.ICE, Blocks.ICE, Blocks.ICE, Blocks.ICE);
    }
    public static List<Block> ufoplating() {
        return List.of(Blocks.WEATHERED_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.EXPOSED_COPPER, Blocks.EXPOSED_CUT_COPPER);
    }
    public static List<Block> ufoglass() {
        return List.of(Blocks.LIME_STAINED_GLASS);
    }
    public static List<Block> ufospecial() {
        return List.of(Blocks.SLIME_BLOCK, ModBlocks.STELLAR_JEWEL_ORE.get(), ModBlocks.STELLAR_BLIGHT.get());
    }


    public static void prefab_meteorsmall(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, genericoresbasic(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, genericmeteorshell(), 1, 1);
    }
    public static void prefab_meteormedium(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, genericoresvaluable(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, genericoresbasic(), 1, 1);
        generateLayerAuto5_ListRandom(level, pos, genericmeteorshell(), 2, 2);
    }
    public static void prefab_meteorlargepoor(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, genericoresvaluable(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, genericoresbasic(), 1, 1);
        generateLayerAuto5_ListRandom(level, pos, genericmeteorshell(), 2, 3);
    }
    public static void prefab_meteorlargerich(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, genericoresvaluable(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, genericoresbasic(), 1, 2);
        generateLayerAuto5_ListRandom(level, pos, genericmeteorshell(), 3, 3);
    }

    public static void prefab_cometsmall(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, cometoresouter(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, genericcometshell(), 1, 1);
    }
    public static void prefab_cometmedium(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, cometoresinner(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, cometoresouter(), 1, 1);
        generateLayerAuto5_ListRandom(level, pos, genericcometshell(), 2, 2);
    }
    public static void prefab_cometmediumpoor(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, cometoresouter(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, cometice(), 1, 1);
        generateLayerAuto5_ListRandom(level, pos, genericcometshell(), 2, 2);
    }
    public static void prefab_cometlargepooricy(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, cometoresinner(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, cometoresouter(), 1, 1);
        generateLayerAuto5_ListRandom(level, pos, cometice(), 2, 2);
        generateLayerAuto5_ListRandom(level, pos, genericcometshell(), 3, 3);
    }
    public static void prefab_cometlargerich(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, cometoresinner(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, cometoresouter(), 1, 2);
        generateLayerAuto5_ListRandom(level, pos, genericcometshell(), 3, 3);
    }

    public static void prefab_jackpot(Level level, BlockPos pos) {
        generateLayerAuto5_ListRandom(level, pos, cometoresinner(), 0, 0);
        generateLayerAuto5_ListRandom(level, pos, cometoresouter(), 1, 1);
        generateLayerAuto5_ListRandom(level, pos, genericoresvaluable(), 2, 2);
        generateLayerAuto5_ListRandom(level, pos, genericoresbasic(), 3, 3);
        generateLayerAuto5_ListRandom(level, pos, genericmeteorshell(), 4, 5);
    }
    public static void prefab_ufo(Level level, BlockPos pos) {
        // holy guacamole this is gonna take forever
        fillrandom(level, pos.below().east().north(), pos.below().west().south(), ufoplating());
        fillrandom(level, pos.north(2).east(), pos.north(3).west(), ufoplating());
        fillrandom(level, pos.south(2).east(), pos.south(3).west(), ufoplating());
        fillrandom(level, pos.east(2).north(), pos.east(3).south(), ufoplating());
        fillrandom(level, pos.west(2).north(), pos.west(3).south(), ufoplating());
        setblockrandomly(level, pos.north(2).east(2), ufoplating());
        setblockrandomly(level, pos.south(2).east(2), ufoplating());
        setblockrandomly(level, pos.north(2).west(2), ufoplating());
        setblockrandomly(level, pos.south(2).west(2), ufoplating());
        fillrandom(level, pos.above().north(2).east(), pos.above().north(2).west(), ufoglass());
        fillrandom(level, pos.above().south(2).east(), pos.above().north(2).west(), ufoglass());
        fillrandom(level, pos.above().east(2).north(), pos.above().east(2).south(), ufoglass());
        fillrandom(level, pos.above().west(2).north(), pos.above().west(2).south(), ufoglass());
        fillrandom(level, pos.above(2).west().north(), pos.above(2).east().south(), ufoglass());
        setblockrandomly(level, pos, ufospecial());
    }
    public static void setblockrandomly(Level level, BlockPos pos, List<Block> blocklist) {
        Block block = blocklist.get((int) (Math.random() * blocklist.size()));
        level.setBlockAndUpdate(pos, block.defaultBlockState());
    }
    public static void fillrandom(Level level, BlockPos pos1, BlockPos pos2, List<Block> blocklist) {
        int xmin = Math.min(pos1.getX(), pos2.getX());
        int ymin = Math.min(pos1.getY(), pos2.getY());
        int zmin = Math.min(pos1.getZ(), pos2.getZ());

        int xmax = Math.max(pos1.getX(), pos2.getX());
        int ymax = Math.max(pos1.getY(), pos2.getY());
        int zmax = Math.max(pos1.getZ(), pos2.getZ());

        for (int x = xmin; x<= xmax; x++) {
            for (int y = ymin; y<= ymax; y++) {
                for (int z = zmin; z<= zmax; z++) {
                    BlockPos newpos = BlockPos.containing(x, y, z);
                    setblockrandomly(level, newpos, blocklist);
                }
            }
        }
    }

    public static void generateComet(Level level, BlockPos pos) {
        int weights = 8 + 5 + 7 + 3 + 2;
        int rand = (int) (Math.random() * weights) + 1;
        if (rand <= 8) {
            prefab_cometsmall(level, pos);
        } else if (rand <= 8+5) {
            prefab_cometmedium(level, pos);
        } else if (rand <= 8+5+7) {
            prefab_cometmediumpoor(level, pos);
        } else if (rand <= 8+5+7+3) {
            prefab_cometlargepooricy(level, pos);
        } else {
            prefab_cometlargerich(level, pos);
        }
    }
    public static void generateMeteor(Level level, BlockPos pos) {
        int weights = 5 + 7 + 3 + 1;
        int rand = (int) (Math.random() * weights) + 1;
        if (rand <= 5) {
            prefab_meteorsmall(level, pos);
        } else if (rand <= 5 + 7) {
            prefab_meteormedium(level, pos);
        } else if (rand <= 5 + 7 + 3) {
            prefab_meteorlargepoor(level, pos);
        } else if (rand <= weights) {
            prefab_meteorlargerich(level, pos);
        }
    }
    public static void generateMeteorAtExactCoords(ServerLevel level, BlockPos pos, PlayerDuck forceloader) {
        level.setBlockAndUpdate(pos, ModBlocks.MGEBLOCK.get().defaultBlockState());
        Set<BlockPos> posset = new HashSet<>();
        posset.add(pos);
        ShipAssembler.AssembleContext ctx = ShipAssembler.assembleToShipFull(level, posset, 1);
        //BlockPos shipyardpos = (BlockPos) ctx.getShip().getShipTransform().getPositionInShip();
        //generateAny(level, shipyardpos);
        forceloader.vs_getPlayer().getForceWatchingShips().add(ctx.getShip().getId());
        Spacerain.ListOfMeteorIds.add(ctx.getShip().getId());

        //ship.setAttachment(new MeteorListenerAttachment(forceloader));
    }
    public static void generateMeteorForRealThisTimeISwear(Player player) {

        BlockPos playerpos = player.blockPosition();
        int angle = (int) (Math.random() * 360); // random angle
        int angledist = (int) (Math.random() * 50) + 25; // meteors spawn 25-75 blocks away from plr
        int xoff = (int) (Math.sin(angle) * angledist);
        int zoff = (int) (Math.cos(angle) * angledist);

        BlockPos playerworldheightpos = new BlockPos(playerpos.getX() + xoff, player.level().getMaxBuildHeight() - 1, playerpos.getZ() + zoff);
        generateMeteorAtExactCoords((ServerLevel) player.level(), playerworldheightpos, (PlayerDuck) player);
        //player.sendSystemMessage(Component.literal(String.valueOf(player.level().players())));
        playmeteorspawnsfx(player.level(), playerworldheightpos);
    }
    public static void playmeteorspawnsfx(Level level, BlockPos soundcenter) {
        for (Player plr : ((ServerLevel) level).players()) {
            // distance falloff maybe? but make it very giant and large n shit
            //plr.sendSystemMessage(Component.literal("test"));
            // cap at 2x
            double dist = plr.blockPosition().getCenter().distanceTo(soundcenter.getCenter());
            double volume = 2 * Math.pow(0.5, (dist/350.0));
            //plr.sendSystemMessage(Component.literal("debug, dist: " + dist));
            //plr.sendSystemMessage(Component.literal("debug, volume: " + volume));
            plr.sendSystemMessage(Component.literal("meteor sfx real"));
            //plr.playSound(ModSounds.METEOR_SPAWN.get(), (float) volume, 1); //sfx not sfxing ffs
            //level.playSound(plr, plr.position().x, plr.position().y, plr.position().z, ModSounds.METEOR_SPAWN.get(), SoundSource.AMBIENT, (float) volume, 1);
            //plr.sendSystemMessage(Component.literal("test3holyfuckingshitcanyouplaythissfx"));

        }

    }
}
