package ssd.curve.spacerain.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;
import java.util.function.Predicate;

public class AstralAltarBlock extends Block {
    public AstralAltarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Item originalItem = pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (RitualScrollItem.class.isAssignableFrom(originalItem.getClass())) {
            RitualScrollItem scrollItem = (RitualScrollItem) originalItem;
            List<Item> req = scrollItem.getItemRequirements();
            Inventory inv = pPlayer.getInventory();
            int totalreq = 0;
            int reqcontained = 0;
            for (Item reqitem : req) {
                totalreq++;
                ItemStack reqitemstack = new ItemStack(reqitem);
                if (inv.contains(reqitemstack)) {
                    reqcontained++;
                }

            }
            if (totalreq == reqcontained) {
                for (Item reqitem : req) {

                    ItemStack reqitemstack = new ItemStack(reqitem);
                    inv.removeItem(inv.findSlotMatchingItem(reqitemstack), 1);

                }
                pLevel.playSound(null, pPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.5f, 1f);
                scrollItem.performRitual(pLevel, pPos, pPlayer);
                coolparticlefx((ServerLevel) pLevel, pPos);


            } else if (pPlayer.isCreative()) {
                pLevel.playSound(null, pPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.5f, 1f);
                scrollItem.performRitual(pLevel, pPos, pPlayer);
                coolparticlefx((ServerLevel) pLevel, pPos);
            } else {
                pPlayer.sendSystemMessage(Component.translatable("altar.spacerain.need_items"));
            }
        } else {
            pPlayer.sendSystemMessage(Component.translatable("altar.spacerain.need_scroll"));
        }


        return InteractionResult.SUCCESS;
    }
    private static void coolparticlefx(ServerLevel pLevel, BlockPos pPos) {
        for (int i=1; i<= 35; i++) {
            pLevel.sendParticles(
                    ParticleTypes.ENCHANT,
                    pPos.getX() + ((Math.random() - 0.5) * 2.0) + 0.5,
                    pPos.getY() + 1.0 + ((Math.random() - 0.5) * 1.0) + 0.5,
                    pPos.getZ() + ((Math.random() - 0.5) * 2.0) + 0.5,
                    3,
                    0,
                    0, 0, 0.1
            );


            pLevel.sendParticles(
                    ParticleTypes.ENCHANT,
                    pPos.getX() + ((Math.random() - 0.5) * 10.0) + 0.5,
                    pPos.getY() + 1.0 + ((Math.random() - 0.5) * 3.0) + 0.5,
                    pPos.getZ() + ((Math.random() - 0.5) * 10.0) + 0.5,
                    3,
                    0,
                    0, 0, 0.1
            );

            pLevel.sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    pPos.getX() + ((Math.random() - 0.5) * 2.0) + 0.5,
                    pPos.getY() + 1.0 + ((Math.random() - 0.5) * 1.0) + 0.5,
                    pPos.getZ() + ((Math.random() - 0.5) * 2.0) + 0.5,
                    2,
                    0,
                    0, 0, 0.1
            );
        }
    }



}
