package ssd.curve.spacerain.network;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.valkyrienskies.core.impl.networking.simple.SimplePacket;

public record SupernovaRenderPacket(BlockPos where, int radius, Level level) implements SimplePacket {

}
