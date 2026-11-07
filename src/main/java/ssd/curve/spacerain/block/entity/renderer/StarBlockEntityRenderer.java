package ssd.curve.spacerain.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.data.ModelData;
import ssd.curve.spacerain.block.ModBlocks;
import ssd.curve.spacerain.block.entity.StarBlockEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class StarBlockEntityRenderer implements BlockEntityRenderer<StarBlockEntity> {
    public StarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(StarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockRenderDispatcher br = Minecraft.getInstance().getBlockRenderer();
        int Temp = pBlockEntity.getTemperature();
        int Inst = pBlockEntity.getInstability();
        //float time = System.currentTimeMillis() / 1000.0f;

        LocalTime now = LocalTime.now();
        float time = now.getSecond() + (now.getNano() / 1_000_000_000f);

        pPoseStack.pushPose();
        //pPoseStack.translate(1.05, -0.05, -0.05);
        pPoseStack.translate(0.5, 0.5, 0.5);
        //pPoseStack.scale(-1.1f, 1.1f, 1.1f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(time*41)); // prime?
        pPoseStack.mulPose(Axis.ZN.rotationDegrees(time*49)); // 7*7
        pPoseStack.mulPose(Axis.YN.rotationDegrees(time*27)); // 3*3*3
        pPoseStack.translate(-0.5, -0.5, -0.5);
        /*pPoseStack.translate(
                rvaltranslate(Inst),
                rvaltranslate(Inst),
                rvaltranslate(Inst));
        */
        //ResourceLocation tex = new ResourceLocation("textures/entity/beacon_beam.png");
        br.renderSingleBlock(Blocks.GLOWSTONE.defaultBlockState(), pPoseStack, pBuffer, 15, pPackedOverlay,
                ModelData.EMPTY, RenderType.lightning());
        pPoseStack.popPose();
    }
    private static double rvaltranslate(int val) {
        return Math.random() * 0.1 * 0.01 * val - 0.05;
    };
}
