package ssd.curve.spacerain.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.Spacerain;

import javax.annotation.Nullable;
//ts is vibed ima be honest
public class ModDamageSources {
    public static final ResourceKey<DamageType> UNWORTHY = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            new ResourceLocation(Spacerain.MODID, "unworthy")
    );
    public static final ResourceKey<DamageType> STELLARIZED = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            new ResourceLocation(Spacerain.MODID, "stellarized")
    );
    public static final ResourceKey<DamageType> SUPERNOVA = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            new ResourceLocation(Spacerain.MODID, "supernova")
    );
    public static final ResourceKey<DamageType> MELTED = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            new ResourceLocation(Spacerain.MODID, "melted")
    );
    public static DamageSource causeDmg(Level level, @Nullable LivingEntity plr, ResourceKey<DamageType> dt) {

        Holder<DamageType> h = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(dt);
        if (plr != null) {
            return new DamageSource(h, plr);
        } else {
            return new DamageSource(h);
        }
    }
}