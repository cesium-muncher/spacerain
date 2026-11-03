package ssd.curve.spacerain.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.util.MeteorImpactCraterManager;

public class StarBlockEntity extends BlockEntity {
    private int light = 0;
    private int medium = 0;
    private int heavy = 0;
    private int exotic = 0;
    private int temperature = 5000; // 5k - 50k
    private int evil = 0;
    private int instability = 0;
    public StarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.STAR_BLOCK_BE.get(), pPos, pBlockState);
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("spacerain_star.light", light);
        pTag.putInt("spacerain_star.medium", medium);
        pTag.putInt("spacerain_star.heavy", heavy);
        pTag.putInt("spacerain_star.exotic", exotic);
        pTag.putInt("spacerain_star.temperature", temperature);
        pTag.putInt("spacerain_star.evil", evil);
        pTag.putInt("spacerain_star.instability", instability);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        light = pTag.getInt("spacerain_star.light");
        medium = pTag.getInt("spacerain_star.medium");
        heavy = pTag.getInt("spacerain_star.heavy");
        exotic = pTag.getInt("spacerain_star.exotic");
        temperature = pTag.getInt("spacerain_star.temperature");
        evil = pTag.getInt("spacerain_star.evil");
        instability = pTag.getInt("spacerain_star.instability");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        // uhhh something idfk yeah
        // light -> medium + temp DONE
        // medium -> heavy + temp DONE
        // exotic -> heavy + evil DONE
        // evil -> instability DONE
        // 95%+ heavy -> instability DONE
        // temperature lowers DONE
        // temp > 30k -> instability lowers
        // temp > 45k -> more instability lowers
        // temp = 5k & heavy = 100 -> turn into iron block lmfao
        // instability = 100 -> star begins shaking or smthn
        //
        if (medium >= 1 && heavy < 100 && (int) (Math.random()*2) == 0) {
            medium--;
            heavy++;
            temperature += 100;
        }
        if (light >= 1 && medium < 100) {
            light--;
            medium++;
            temperature += 100;
        }
        if (exotic >= 1 && heavy < 100) {
            exotic--;
            heavy++;
            temperature += 100;
            evil++;
        }
        if (heavy >= 100 && instability < 101&& (int) (Math.random()*3) == 0) {
            instability++;
        }
        if (heavy >= 95 && instability < 101) {
            instability++;
        }
        if (evil >= 50&& (int) (Math.random()*20) == 0) {
            instability++;
        }
        if (instability > 0) {
            instability--;
        }

        if (temperature > 5000) {
            temperature -= 1;
        }
        if (temperature <= 6000 && heavy >= 95) {
            pLevel.setBlockAndUpdate(pPos, Blocks.IRON_BLOCK.defaultBlockState());
        }
        if (instability >= 101) {
            //MeteorImpactCraterManager.cratergen(pLevel, pPos);

        }
        for (Player plr : pLevel.getServer().getPlayerList().getPlayers()) {
            //plr.sendSystemMessage(Component.literal("fart, " + light));
        }
    }
    public int getTemperature() {
        return temperature;
    }
    public int getEvil() {
        return evil;
    }
    public int  getInstability() {
        return instability;
    }

    public void click(Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        // solar tongs: extracts heavy elements
        // spectrograph: says element contents

        Item item = pPlayer.getItemInHand(pHand).getItem();
        if (item == ModItems.SPECTROGRAPH.get()) {
            pPlayer.sendSystemMessage(Component.literal(Component.translatable("star.spacerain.element.light").getString() + light));
            pPlayer.sendSystemMessage(Component.literal(Component.translatable("star.spacerain.element.medium").getString() + medium));
            pPlayer.sendSystemMessage(Component.literal(Component.translatable("star.spacerain.element.heavy").getString() + heavy));
            pPlayer.sendSystemMessage(Component.literal(Component.translatable("star.spacerain.element.exotic").getString() + exotic));
            pPlayer.sendSystemMessage(Component.literal(Component.translatable("star.spacerain.temp").getString() + temperature));
            pPlayer.sendSystemMessage(Component.literal(Component.translatable("star.spacerain.evil").getString() + evil));
            pPlayer.sendSystemMessage(Component.literal(Component.translatable("star.spacerain.instability").getString() + instability));
        } else if (item == ModItems.SOLAR_TONGS.get()) {
            if (heavy >= 10) {
                heavy -= 10;
                if (!pPlayer.getInventory().add(new ItemStack(Items.IRON_INGOT))) {
                   pPlayer.drop(new ItemStack(Items.IRON_INGOT), false);
                }
            }
        } else if (item == ModItems.LIGHT_ELEMENTS.get()) {
            if (light <= 90) {
                light += 10;
                int slot = Inventory.SLOT_OFFHAND;
                if (pHand == InteractionHand.MAIN_HAND) {
                    slot = pPlayer.getInventory().selected;
                }
                pPlayer.getInventory().removeItem(slot, 1);
                giveitem(pPlayer, ModItems.COBALT_BOTTLE.get());
            }
        } else if (item == ModItems.MEDIUM_ELEMENTS.get()) {
            if (medium <= 90) {
                medium += 10;
                int slot = Inventory.SLOT_OFFHAND;
                if (pHand == InteractionHand.MAIN_HAND) {
                    slot = pPlayer.getInventory().selected;
                }
                pPlayer.getInventory().removeItem(slot, 1);
                giveitem(pPlayer, ModItems.COBALT_BOTTLE.get());
            }
        } else if (item == ModItems.HEAVY_ELEMENTS.get()) {
            if (heavy <= 90) {
                heavy += 10;
                int slot = Inventory.SLOT_OFFHAND;
                if (pHand == InteractionHand.MAIN_HAND) {
                    slot = pPlayer.getInventory().selected;
                }
                pPlayer.getInventory().removeItem(slot, 1);
                giveitem(pPlayer, ModItems.COBALT_BOTTLE.get());
            }
        } else if (item == ModItems.EXOTIC_ELEMENTS.get()) {
            if (exotic <= 90) {
                exotic += 10;
                int slot = Inventory.SLOT_OFFHAND;
                if (pHand == InteractionHand.MAIN_HAND) {
                    slot = pPlayer.getInventory().selected;
                }
                pPlayer.getInventory().removeItem(slot, 1);
                giveitem(pPlayer, ModItems.COBALT_BOTTLE.get());
            }
        } else if (item == ModItems.COBALT_BOTTLE.get()) {
            if (heavy >= 10) {
                heavy -= 10;
                int slot = Inventory.SLOT_OFFHAND;
                if (pHand == InteractionHand.MAIN_HAND) {
                    slot = pPlayer.getInventory().selected;
                }
                pPlayer.getInventory().removeItem(slot, 1);
                giveitem(pPlayer, ModItems.HEAVY_ELEMENTS.get());
            }
        } else if (item == ModItems.FALLEN_SIGIL.get()) {
            if (evil >= 10) {
                evil -= 10;
                int slot = Inventory.SLOT_OFFHAND;
                if (pHand == InteractionHand.MAIN_HAND) {
                    slot = pPlayer.getInventory().selected;
                }
                pPlayer.getInventory().removeItem(slot, 1);
                giveitem(pPlayer, ModItems.AWAKENED_SIGIL.get());
            }
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
    private static void giveitem(Player pPlayer, Item pItem) {
        if (!pPlayer.getInventory().add(new ItemStack(pItem))) {
            pPlayer.drop(new ItemStack(pItem), false);
        }
    }
}
