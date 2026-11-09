package ssd.curve.spacerain.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
public class BrightSupernovaFlashScreen {
    


    public static IGuiOverlay BRIGHT_SUPERNOVA_FLASH = (gui, guiGraphics, partialTick, width, height) -> {

        LocalPlayer plr = Minecraft.getInstance().player;
        HitResult hr = plr.pick(plr.getBlockReach(), partialTick, false);
        int alph = SupernovaRenderer.eyepain;
        if (alph > 0) {
            alph = Math.max(0, Math.min(255, alph));
            int hex = ((alph & 0xFF) << 24) |
                    ((0xFF) << 16) |
                    ((0xFF) << 8)  |
                    (0xFF);

            guiGraphics.fill(0, 0, width, height, hex);
        }


    };

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("bright_supernova_flash", BRIGHT_SUPERNOVA_FLASH);
    }
}
