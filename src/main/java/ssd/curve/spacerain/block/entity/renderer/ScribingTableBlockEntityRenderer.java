package ssd.curve.spacerain.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.data.ModelData;
import ssd.curve.spacerain.block.entity.ScribingTableBlockEntity;
import ssd.curve.spacerain.block.entity.StarBlockEntity;
import ssd.curve.spacerain.item.ModItems;

import java.time.LocalTime;

public class ScribingTableBlockEntityRenderer implements BlockEntityRenderer<ScribingTableBlockEntity> {
    public ScribingTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(ScribingTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockRenderDispatcher br = Minecraft.getInstance().getBlockRenderer();
        ItemRenderer ir = Minecraft.getInstance().getItemRenderer();

        //float time = System.currentTimeMillis() / 1000.0f;

        LocalTime now = LocalTime.now();
        float time = now.getSecond() + (now.getNano() / 1_000_000_000f);

        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 1.5, 0.5);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(time*51));
        pPoseStack.scale(0.75f, 0.75f, 0.75f);
        pPoseStack.mulPose(Axis.YP.rotationDegrees((float) Math.pow(pBlockEntity.progress, 1.05)));

        ir.renderStatic(pBlockEntity.getRenderStack(), ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
