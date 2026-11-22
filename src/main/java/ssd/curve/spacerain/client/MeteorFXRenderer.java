package ssd.curve.spacerain.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
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
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MeteorFXRenderer {



    public static Queue<MeteorVisualData> MeteorsToRender = new ConcurrentLinkedQueue<>();

    public static void tickevent(TickEvent.ClientTickEvent event) {
        Queue<MeteorVisualData> nq = new ConcurrentLinkedQueue<>(MeteorsToRender);
        MeteorsToRender.clear();
        for (MeteorVisualData sd : nq) {
            int t = sd.info;
            BlockPos p = sd.pos;




            if (t == 2) {
                Color pcol1 = new Color(255, 60, 0);
                Color pcol2 = new Color(255, 250, 200);
                Color pcol3 = new Color(5, 5, 5);

                Level l = Minecraft.getInstance().level;
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                        .setScaleData(GenericParticleData.create(7, 0).build())
                        .enableNoClip()
                        .setLifetime(5)
                        .setColorData(ColorParticleData.create(pcol1, pcol2).build())
                        //.setRenderType(ParticleRenderType.PARTICLE_SHEET_LIT)
                        //.setSpinData(SpinParticleData.create((float) (Math.random() * 360 - 180)).build())
                        .enableForcedSpawn()
                        .spawn(l, p.getCenter().x, p.getCenter().y, p.getCenter().z);


                WorldParticleBuilder.create(ModLodestoneParticles.METEOR_SMOKE)
                        .setScaleData(GenericParticleData.create(4, 0).build())
                        .enableNoClip()
                        .setLifetime(600)
                        .setColorData(ColorParticleData.create(pcol3, pcol3).build())
                        .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)

                        //.setSpinData(SpinParticleData.create((float) (Math.random() * 360 - 180)).build())
                        .enableForcedSpawn()
                        .spawn(l, p.getCenter().x, p.getCenter().y, p.getCenter().z);
            }
            if (t == 1) {
                double dist = Minecraft.getInstance().player.position().distanceTo(p.getCenter());
                // fuck you get linear'd
                // dist 1024 = volume 0
                // dist 0 = volume 1
                double volume = Math.max(-(dist/1024.0) + 1.0, 0);
                Minecraft.getInstance().player.playSound(ModSounds.METEOR_SPAWN.get(), (float) volume, 1);
            }
            if (t == 3) {
                double dist = Minecraft.getInstance().player.position().distanceTo(p.getCenter());
                // fuck you get linear'd
                // dist 1024 = volume 0
                // dist 0 = volume 1
                double volume = Math.max(-(dist/1024.0) + 1.0, 0);
                Minecraft.getInstance().player.playSound(ModSounds.METEOR_IMPACT.get(), (float) volume, 1);
            }







        }

    }
}
