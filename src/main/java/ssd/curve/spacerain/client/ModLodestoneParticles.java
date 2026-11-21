package ssd.curve.spacerain.client;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ssd.curve.spacerain.Spacerain;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.particle.type.LodestoneItemCrumbsParticleType;
import team.lodestar.lodestone.systems.particle.type.LodestoneParticleType;
import team.lodestar.lodestone.systems.particle.type.LodestoneSparkParticleType;
import team.lodestar.lodestone.systems.particle.type.LodestoneTerrainParticleType;

public class ModLodestoneParticles {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Spacerain.MODID);

    public static RegistryObject<LodestoneParticleType> METEOR_SMOKE = PARTICLES.register("meteor_smoke", LodestoneParticleType::new);

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(METEOR_SMOKE.get(), LodestoneParticleType.Factory::new);
    }
    public static void register(IEventBus eventbus) {
        PARTICLES.register(eventbus);
    }
}
