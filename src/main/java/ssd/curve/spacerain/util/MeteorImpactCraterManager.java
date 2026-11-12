package ssd.curve.spacerain.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.api.ValkyrienSkies;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MeteorImpactCraterManager {
    public static Queue<Long> meteorsExploding = new ConcurrentLinkedQueue<>();
    public static Queue<Long> meteorfallparticles = new ConcurrentLinkedQueue<>();
    public volatile static boolean dontFUCKINGmodifytheselists = false; // useless cuz im using concurrent linked queue now

    public static void checkChanges(TickEvent.ServerTickEvent event) {
        //System.out.println("freaking, armenia");
        if (!meteorsExploding.isEmpty()) {
            Queue<Long> newmetexp = new ConcurrentLinkedQueue<>(meteorsExploding);

            for (Long meteorid : newmetexp) {
                meteorsExploding.remove(meteorid);
                List<ServerPlayer> people = event.getServer().getPlayerList().getPlayers();
                ServerShip servermeteor = ValkyrienSkiesMod.getApi().getServerShipWorld(event.getServer()).getAllShips().getById(meteorid);

                Level level = VSGameUtilsKt.getLevelFromDimensionId(event.getServer(), servermeteor.getChunkClaimDimension());


                if (level != null) {


                    Vector3dc shipcenter = servermeteor.getTransform().getPosition();
                    Vector3d worldcenter = ValkyrienSkies.positionToWorld(level, shipcenter, new Vector3d());
                    BlockPos center = BlockPos.containing(worldcenter.x, worldcenter.y, worldcenter.z);
                    BlockPos floor = center.below(3);
                    cratergen(level, floor);
                }


            }


        }
    }

    public static void cratergen(Level level, BlockPos center) {
        int wradius = 5;
        int hradius = 3;
        for (int x=-wradius; x<= wradius; x++) {
            for (int z=-wradius; z<= wradius; z++) {
                for (int y=-hradius; y<= hradius; y++) {
                    if (x*x+y*y+z*z <= Math.pow((Math.random() * 3.0)+2.5, 2.0)) {
                        // wait, how the fuck do i get the level?
                        BlockPos pos = center.offset(x, y, z);
                        BlockState bs = level.getBlockState(pos);
                        if (bs.getBlock().getExplosionResistance() < 1000) {
                            level.destroyBlock(pos, true, null, 0);
                            //level.explode();
                        }
                    }
                }
            }
        }
    }

    public static void renderparticles(TickEvent.ServerTickEvent event) {

        if (!meteorfallparticles.isEmpty()) {
            Queue<Long> newmfp = new ConcurrentLinkedQueue<>(meteorfallparticles);
            for (Long meteorid : newmfp) {
                List<ServerPlayer> people = event.getServer().getPlayerList().getPlayers();
                ServerShip servermeteor = ValkyrienSkiesMod.getApi().getServerShipWorld(event.getServer()).getAllShips().getById(meteorid);

                ServerLevel level = VSGameUtilsKt.getLevelFromDimensionId(event.getServer(), servermeteor.getChunkClaimDimension());
                meteorfallparticles.remove(newmfp);

                if (level != null) {


                    Vector3dc shipcenter = servermeteor.getTransform().getPosition();
                    Vector3d worldcenter = ValkyrienSkies.positionToWorld(level, shipcenter, new Vector3d());
                    BlockPos center = BlockPos.containing(worldcenter.x, worldcenter.y, worldcenter.z);
                    level.sendParticles(ParticleTypes.CLOUD, worldcenter.x, worldcenter.y, worldcenter.z, 25, 1, 1,1,0.1);
                }


            }


        }
    }
}
