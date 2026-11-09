package ssd.curve.spacerain.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.block.ModBlocks;
import ssd.curve.spacerain.block.entity.StarBlockEntity;
import ssd.curve.spacerain.item.ModItems;

import java.awt.*;

@Mod.EventBusSubscriber(modid = Spacerain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StarSpectrographScreen {
    

    private static void drawtextfast(ForgeGui gui, GuiGraphics guiGraphics, int width, int height, int offset, Component tc) {
        guiGraphics.drawString(
                gui.getFont(),
                tc,
                width/2 + 5,
                height/2 + offset,
                0xFFFFFF,
                true
        );
    }
    public static IGuiOverlay STAR_INFO_OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {

        LocalPlayer plr = Minecraft.getInstance().player;
        HitResult hr = plr.pick(plr.getBlockReach(), partialTick, false);
        if (hr.getType() == HitResult.Type.BLOCK) {
            BlockHitResult bhr = (BlockHitResult) hr;
            BlockState bs = plr.level().getBlockState(bhr.getBlockPos());
            if (bs.is(ModBlocks.STAR.get())) {
                StarBlockEntity sbr = (StarBlockEntity) plr.level().getBlockEntity(bhr.getBlockPos());



                Item inhand1 = plr.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                Item inhand2 = plr.getItemInHand(InteractionHand.OFF_HAND).getItem();

                if (inhand1 == ModItems.SPECTROGRAPH.get() || inhand2 == ModItems.SPECTROGRAPH.get()) {
                    guiGraphics.fill(width/2 + 2, height/2 +2, width/2 + 116, height/2 +75, 0x7f7f7f7f);
                    drawtextfast(gui, guiGraphics, width, height, 15, Component.literal(Component.translatable("star.spacerain.element.light").getString() + sbr.light));
                    drawtextfast(gui, guiGraphics, width, height, 25, Component.literal(Component.translatable("star.spacerain.element.medium").getString() + sbr.medium));
                    drawtextfast(gui, guiGraphics, width, height, 35, Component.literal(Component.translatable("star.spacerain.element.heavy").getString() + sbr.heavy));
                    drawtextfast(gui, guiGraphics, width, height, 45, Component.literal(Component.translatable("star.spacerain.element.exotic").getString() + sbr.exotic));
                    drawtextfast(gui, guiGraphics, width, height, 55, Component.literal(Component.translatable("star.spacerain.evil").getString() + sbr.evil + "%"));
                    drawtextfast(gui, guiGraphics, width, height, 65, Component.literal(Component.translatable("star.spacerain.instability").getString() + sbr.instability + "%"));
                } else {
                    guiGraphics.fill(width/2 + 2, height/2 +2, width/2 + 108, height/2 +15, 0x7f7f7f7f);
                }

                drawtextfast(gui, guiGraphics, width, height, 5, Component.literal(Component.translatable("star.spacerain.temp").getString() + sbr.temperature + "K"));





            }


        }


    };

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("star_info_overlay", STAR_INFO_OVERLAY);
    }
}
