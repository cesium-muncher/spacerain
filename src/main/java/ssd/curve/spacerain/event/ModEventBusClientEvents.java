package ssd.curve.spacerain.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.block.entity.ModBlockEntities;
import ssd.curve.spacerain.block.entity.renderer.StarBlockEntityRenderer;

@Mod.EventBusSubscriber(modid = Spacerain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.STAR_BLOCK_BE.get(), StarBlockEntityRenderer::new);
    }
}
