package ssd.curve.spacerain.forgepackets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import ssd.curve.spacerain.client.SupernovaData;
import ssd.curve.spacerain.client.SupernovaRenderer;

import java.util.function.Supplier;

public class ForgeMPPacket { // meteor particle packet
    private final BlockPos pos;
    private final int info; // 1 = entry sfx, 2 = smoke, 3 = land sfx, 4 = land smoke

    public ForgeMPPacket(BlockPos pos, int info) {
        this.info = info;
        this.pos = pos;

    }


    public ForgeMPPacket(FriendlyByteBuf buf) {
        this.info = buf.readInt();
        this.pos = buf.readBlockPos();


    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.info);
        buf.writeBlockPos(this.pos);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {



        });
        return true;
    }
}
