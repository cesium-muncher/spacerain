package ssd.curve.spacerain.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ssd.curve.spacerain.Spacerain;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Spacerain.MODID);

    public static final RegistryObject<SoundEvent> METEOR_SPAWN = registerSoundEvents("meteor_spawn");

    private static RegistryObject<SoundEvent> registerSoundEvents(String s) {
        return SOUND_EVENTS.register(s, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Spacerain.MODID, s)));
    }


    public static void register(IEventBus eventbus) {
        SOUND_EVENTS.register(eventbus);
    }
}
