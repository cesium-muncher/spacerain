package ssd.curve.spacerain.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import ssd.curve.spacerain.sound.ModSounds;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MeteorFXRenderer {



    public static Queue<MeteorVisualData> MeteorsToRender = new ConcurrentLinkedQueue<>();

    public static void tickevent(TickEvent.ClientTickEvent event) {

        for (MeteorVisualData sd : MeteorsToRender) {
            int t = sd.info;
            BlockPos p = sd.pos;








            /*WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(t/2.0f, 0).build())
                    .enableNoClip()
                    .setLifetime(5)
                    .setColorData(ColorParticleData.create(pcol1, pcol2).build())
                    .setSpinData(SpinParticleData.create((float) (Math.random() * 360 - 180)).build())
                    .enableForcedSpawn()
                    .spawn(l, p.getCenter().x, p.getCenter().y, p.getCenter().z);
*/




        }

    }
}
