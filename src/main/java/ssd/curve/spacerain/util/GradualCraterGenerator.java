package ssd.curve.spacerain.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraftforge.event.TickEvent;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.api.ValkyrienSkies;
import ssd.curve.spacerain.forgepackets.ForgeSNPacket;
import ssd.curve.spacerain.forgepackets.ModChannel;

import javax.annotation.Nullable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GradualCraterGenerator {
    private BlockPos pos;
    private Level plevel;
    private int layer = 0;
    private int radius;
    private ImprovedNoise noise;
    private boolean stop;

    public static Queue<GradualCraterGenerator> gcds = new ConcurrentLinkedQueue<>(); // why can i do this
    public GradualCraterGenerator(Level pLevel, BlockPos pos, int radius) {
        this.pos = pos;
        this.plevel = pLevel;
        this.layer = 0;
        RandomSource rs = RandomSource.create((long) (Math.random()*100000));
        this.noise = new ImprovedNoise(rs);
        this.stop = false;
        this.radius = radius;
    }
    public void advance() {
        if (!stop) {
            if (layer == 0) {
                SupernovaManager.fuckingkilleveryone(plevel, pos, radius);
                SupernovaManager.loudassnoise(plevel, pos);
                SupernovaManager.SupernovaGenVericalColumn(plevel, pos, radius, pos, noise);
                SupernovaManager.giveadvancement(plevel, pos, radius);
            } else {
                SupernovaManager.SupernovaGenSquareLayer(plevel, pos, radius, layer, noise);
                SupernovaManager.loudassnoise(plevel, pos);
            }

            layer++;
            if (layer > radius) {
                // uhhh... stop?
                stop = true;
            }
        }

    }
    public boolean isstopped() {
        return stop;
    }
    private static Queue<GradualCraterGenerator> kill_me = new ConcurrentLinkedQueue<>();
    public static void gcd_tickevent(TickEvent.ServerTickEvent event) {

        for (GradualCraterGenerator gcd : gcds) {
            gcd.advance();
            if (gcd.isstopped()) {
                kill_me.add(gcd);
            }
        }
        for (GradualCraterGenerator kill : kill_me) {
            gcds.remove(kill);
        }
        kill_me.clear();
    }
    public static void generic_supernova_spawn(Level level, BlockPos pos) {
        supernova_spawn(level, pos, 50);
    }
    public static void supernova_spawn(Level level, BlockPos pos, int radius) {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        @Nullable Ship ship = ValkyrienSkies.getShipManagingBlock(level, pos);
        if (ship != null) {
            pos = BlockPos.containing(ValkyrienSkies.positionToWorld(ship, pos.getCenter()));
        }



        GradualCraterGenerator gcd = new GradualCraterGenerator(level, pos, radius);
        gcds.add(gcd);
        for (Player plr : level.players()) {
            ModChannel.sendToPlayer(new ForgeSNPacket(pos, radius), (ServerPlayer) plr);
            //plr.sendSystemMessage(Component.literal("server: " + pPos.toShortString()));
        }
    }
}
