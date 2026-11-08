package ssd.curve.spacerain.client;

import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SupernovaRenderer {

    public static Queue<SupernovaData> SupernovasToRender = new ConcurrentLinkedQueue<>();

    public static void tickevent(TickEvent.ClientTickEvent event) {
        for (SupernovaData sd : SupernovasToRender) {
            int r = sd.radius;
            BlockPos p = sd.pos;
            Level l = sd.level;

            int t = sd.tickcount;
            sd.tickcount++;
            Color pcol1 = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
            Color pcol2 = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(t, 0).build())
                    .enableNoClip()
                    .setLifetime((int) Math.ceil(0.2 * r))
                    .setColorData(ColorParticleData.create(pcol1, pcol2).build())
                    .enableForcedSpawn()
                    .spawn(l, p.getCenter().x, p.getCenter().y, p.getCenter().z);

            if (sd.tickcount > r) {
                SupernovasToRender.remove(sd);
            }

        }
    }
    public static void debugSupernovaRender(Level level, BlockPos pos) {
        SupernovaData d = new SupernovaData(pos, 50, level);
        SupernovasToRender.add(d);
    }
}
