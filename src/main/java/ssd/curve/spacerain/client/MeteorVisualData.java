package ssd.curve.spacerain.client;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class MeteorVisualData {

    public int info = 0;
    public BlockPos pos;


    public MeteorVisualData(BlockPos p, int r, Level l) {
        this.info = r;
        this.pos = p;

    }
}
