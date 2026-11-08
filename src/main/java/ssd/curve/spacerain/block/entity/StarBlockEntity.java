package ssd.curve.spacerain.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import ssd.curve.spacerain.client.SupernovaRenderer;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.util.GradualCraterGenerator;
import ssd.curve.spacerain.util.MeteorImpactCraterManager;
import ssd.curve.spacerain.util.SupernovaManager;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;

public class StarBlockEntity extends BlockEntity {
    private int light = 0;
    private int medium = 0;
    public int heavy = 0;
    private int exotic = 0;
    private int temperature = 5000; // 5k - 50k
    private int evil = 0;
    private int instability = 0;
    public StarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.STAR_BLOCK_BE.get(), pPos, pBlockState);
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("spacerain_star.light", light);
        pTag.putInt("spacerain_star.medium", medium);
        pTag.putInt("spacerain_star.heavy", heavy);
        pTag.putInt("spacerain_star.exotic", exotic);
        pTag.putInt("spacerain_star.temperature", temperature);
        pTag.putInt("spacerain_star.evil", evil);
        pTag.putInt("spacerain_star.instability", instability);

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
    private static Color colorLerp(Color c1, Color c2, double ratio) {
        int r = (int) (c1.getRed() * (1-ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1-ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1-ratio) + c2.getBlue() * ratio);
        r = Math.min(255, Math.max(0, r));
        g = Math.min(255, Math.max(0, g));
        b = Math.min(255, Math.max(0, b));
        return new Color(r, g, b);
    }
    /**
     * IMPORTANT PLS READ IF YOU ARE WEIRD AND USING THIS FOR SOME REASON (this is a blockentity twin????)
     * temp is actually off by a factor of like 5 btw
     * to get 1:1 uhh you should umm multiply input by 5
     * this thing sees 33000K as 6600K
     */
    public static Color blackbodyRadiationCalc(int temp) { // actually the incorrect values but idc
        float t = temp/500.0f;
        float bbr;
        float bbg;
        float bbb;

        if (t<= 66) {
            bbr = 255;
            bbg = t;
            bbg = (float) (99.4708025861 * Math.log(bbg) - 161.1195681661);
            if (t <= 19) {
                bbb = 0;
            } else {
                bbb = t-10;
                bbb = (float) (138.5177312231 * Math.log(bbb) - 305.0447927307);
            }
        } else {
            bbr = t-60;
            bbr = (float) (329.698727446 * Math.pow(bbr, -0.1332047592));
            bbg = t-60;
            bbg = (float) (288.1221695283 * Math.pow(bbg, -0.0755148492 ));
            bbb = 255;
        }

        bbr = Math.min(255, Math.max(0, bbr));
        bbg = Math.min(255, Math.max(0, bbg));
        bbb = Math.min(255, Math.max(0, bbb));
        return new Color((int)bbr, (int)bbg, (int)bbb);
    }

    public void clientTick(Level level, BlockPos pos, BlockState state) {
        // ouhhh ai code ooooo vibecoding oooooo goggle ai overview (just clienttick stuff not the colors shit)
        if (level.isClientSide) {
            if (instability == 99) { // remove later when u figure out packets, will only work on singleplayer or legendary connection prob
                SupernovaRenderer.debugSupernovaRender(level, pos);
            }
            Color particlecolor = new Color((int) (heavy * 2.55), 0, 0);


            // ok so uhh umm
            // temp: set color
            // instability: randomness in size
            // evil: make slightly redder
            // sum of elements: change particle size

            float size = (float) (((light+medium+heavy+exotic) / 100.0f) + Math.random() * instability * 0.01f) + 1.5f;

            Color BlackbodyColor = blackbodyRadiationCalc(temperature);
            Color evilColor = new Color(255, 0, 0);
            Color FinalColor = colorLerp(BlackbodyColor, evilColor, evil*0.01);


            WorldParticleBuilder.create(LodestoneParticleRegistry.STAR_PARTICLE)
                    .setScaleData(GenericParticleData.create(size, 0).build())
                    .enableNoClip()
                    .setLifetime(5)
                    .setColorData(ColorParticleData.create(BlackbodyColor, BlackbodyColor).build())
                    .enableForcedSpawn()
                    .spawn(level, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z);
        }
    }
    private static int tickcounter = 0;
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        tickcounter++;

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
        level.sendBlockUpdated(pPos, pState, pState, Block.UPDATE_ALL); // moved here specifically just because i need to test the kaboom fx
        if (instability >= 100) {
            GradualCraterGenerator.generic_supernova_spawn(pLevel, pPos);
            // kablewy
        }
        //for (Player plr : pLevel.getServer().getPlayerList().getPlayers()) {
            //plr.sendSystemMessage(Component.literal("fart, " + light));
        //}


        if (tickcounter % 20 == 0) {
            setChanged();
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
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1, 1, 1);
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
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1, 1, 1);
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
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1, 1, 1);
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
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1, 1, 1);
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
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.BLOCKS, 1, 1, 1);
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

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            load(tag); // bro just load ffs load it you have the fucken tag
        }
        super.onDataPacket(net, pkt);
    }

}
