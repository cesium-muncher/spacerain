package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollAwaken extends RitualScrollItem {
    public ScrollAwaken(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {

        // wake up? idk

    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(ModItems.FALLEN_SIGIL.get(), ModItems.ASTRAL_JEWEL.get(), ModItems.COBALT_INGOT.get());
    }
}
