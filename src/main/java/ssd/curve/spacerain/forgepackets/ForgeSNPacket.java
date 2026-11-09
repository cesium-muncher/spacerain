package ssd.curve.spacerain.forgepackets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkEvent;
import ssd.curve.spacerain.client.SupernovaData;
import ssd.curve.spacerain.client.SupernovaRenderer;

import java.util.function.Supplier;

public class ForgeSNPacket {
    private final BlockPos pos;
    private final int radius;

    public ForgeSNPacket(BlockPos pos, int radius) {
        this.radius = radius;
        this.pos = pos;

    }


    public ForgeSNPacket(FriendlyByteBuf buf) {
        this.radius = buf.readInt();
        this.pos = buf.readBlockPos();


    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.radius);
        buf.writeBlockPos(this.pos);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            SupernovaData d = new SupernovaData(pos, radius, Minecraft.getInstance().level);
            SupernovaRenderer.SupernovasToRender.add(d);

        });
        return true;
    }
}
