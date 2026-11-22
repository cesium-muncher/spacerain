package ssd.curve.spacerain.item.custom;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.util.GradualCraterGenerator;
import ssd.curve.spacerain.util.ModDamageSources;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StellarOmicronItem extends SwordItem {

    public StellarOmicronItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.hurt(ModDamageSources.causeDmg(pTarget.level(), pAttacker, ModDamageSources.STELLARIZED), 200);
        //GradualCraterGenerator.supernova_spawn(pTarget.level(), BlockPos.containing(pTarget.position()), 2);
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);

        });
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (!pLevel.isClientSide) {
            BlockHitResult lookatres = getPlayerPOVHitResultCustomRange(pLevel, pPlayer, ClipContext.Fluid.NONE, 50);
            BlockPos pPos = lookatres.getBlockPos();
            GradualCraterGenerator.supernova_spawn(pLevel, pPos, 15);
            pPlayer.getCooldowns().addCooldown(ModItems.STELLAR_COBALT_OMICRON.get(), 20*30);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 50;
    }

    protected static BlockHitResult getPlayerPOVHitResultCustomRange(Level pLevel, Player pPlayer, ClipContext.Fluid pFluidMode, double hitrange) {
        float f = pPlayer.getXRot();
        float f1 = pPlayer.getYRot();
        Vec3 vec3 = pPlayer.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = hitrange; //pPlayer.getAttributeValue(net.minecraftforge.common.ForgeMod.BLOCK_REACH.get()) + 0.5; // Block reach is 4.5, vanilla uses 5.0 here, so add 0.5 padding
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, pFluidMode, pPlayer));
    }
}

