package ssd.curve.spacerain.client;

import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import ssd.curve.spacerain.sound.ModSounds;
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

    public static int eyepain = 0;

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
                    .setScaleData(GenericParticleData.create(t/2.0f, 0).build())
                    .enableNoClip()
                    .setLifetime(5)
                    .setColorData(ColorParticleData.create(pcol1, pcol2).build())
                    .setSpinData(SpinParticleData.create((float) (Math.random() * 360 - 180)).build())
                    .enableForcedSpawn()
                    .spawn(l, p.getCenter().x, p.getCenter().y, p.getCenter().z);

            if (sd.tickcount > r*2) {
                SupernovasToRender.remove(sd);
            }

            double dist = Minecraft.getInstance().player.position().distanceTo(p.getCenter());
            float sfxvolume = (float) Math.pow(0.5, dist/(r)) * 3;
            Minecraft.getInstance().player.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, sfxvolume, 1);
            if (t == 1) {

                eyepain += (int) Math.max(0, 500 - dist * r * 0.01);
                Minecraft.getInstance().player.playSound(ModSounds.SUPERNOVA.get(), sfxvolume, 1);
                //Minecraft.getInstance().player.sendSystemMessage(Component.literal("dist to sn:" + dist));

                WorldParticleBuilder.create(LodestoneParticleRegistry.STAR_PARTICLE)
                        .setScaleData(GenericParticleData.create(r * 3, 0).build())
                        .enableNoClip()
                        .setLifetime(400)
                        .setColorData(ColorParticleData.create(Color.red, Color.red).build())

                        .enableForcedSpawn()
                        .spawn(l, p.getCenter().x, p.getCenter().y, p.getCenter().z);
                /*WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                        .setScaleData(GenericParticleData.create(0, r).build())
                        .enableNoClip()
                        .setLifetime(r*3)
                        .setColorData(ColorParticleData.create(pcol1, pcol2).build())
                        .setSpinData(SpinParticleData.create((float) (Math.random() * 360 - 180)).build())
                        .enableForcedSpawn()
                        .spawn(l, p.getCenter().x, p.getCenter().y, p.getCenter().z);
*/
            }

        }
        if (eyepain > 0) {
            eyepain -= 1;
        }
        if (eyepain > 255) {
            eyepain = 255;
        }
    }
    public static void debugSupernovaRender(Level level, BlockPos pos) {
        SupernovaData d = new SupernovaData(pos, 50, level);
        SupernovasToRender.add(d);
    }
}
