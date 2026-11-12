package ssd.curve.spacerain.forgepackets;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import ssd.curve.spacerain.Spacerain;

public class ModChannel {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Spacerain.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ForgeSNPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ForgeSNPacket::new)
                .encoder(ForgeSNPacket::toBytes)
                .consumerMainThread(ForgeSNPacket::handle)
                .add();

        net.messageBuilder(ForgeMPPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ForgeMPPacket::new)
                .encoder(ForgeMPPacket::toBytes)
                .consumerMainThread(ForgeMPPacket::handle)
                .add();


    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAllClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
