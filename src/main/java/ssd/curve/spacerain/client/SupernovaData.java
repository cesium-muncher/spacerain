package ssd.curve.spacerain.client;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class SupernovaData {
    public int tickcount = 0;
    public int radius = 0;
    public BlockPos pos;
    public Level level;

    public SupernovaData(BlockPos p, int r, Level l) {
        this.radius = r;
        this.pos = p;
        this.level = l;
    }
}
