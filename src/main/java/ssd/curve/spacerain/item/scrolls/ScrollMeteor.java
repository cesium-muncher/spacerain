package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.block.ModBlocks;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;
import ssd.curve.spacerain.util.MeteorGenerator;

import java.util.List;

public class ScrollMeteor extends RitualScrollItem {
    public ScrollMeteor(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {

        MeteorGenerator.generateMeteorForRealThisTimeISwear(player);


        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(ModBlocks.METEORITE_BLOCK.get().asItem(), ModItems.AWAKENED_SIGIL.get());
    }
}
