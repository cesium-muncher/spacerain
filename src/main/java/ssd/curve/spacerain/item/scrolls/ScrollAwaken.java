package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.block.ModBlocks;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;
import ssd.curve.spacerain.util.ModDamageSources;

import java.util.List;

public class ScrollAwaken extends RitualScrollItem {
    public ScrollAwaken(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {


        if (level.getBlockState(pos).getBlock() == ModBlocks.ASTRAL_ALTAR.get()) {
            player.hurt(ModDamageSources.causeDmg(level, null, ModDamageSources.UNWORTHY), 20000);
            //player.setSecondsOnFire(3); nahhhh idk
            if (player.getHealth() > 0) {
                level.destroyBlock(pos.above(2), true, null);
                level.setBlockAndUpdate(pos.above(2), ModBlocks.STAR.get().defaultBlockState());
            } else {
                player.sendSystemMessage(Component.translatable("altar.spacerain.weakling"));
            }
        } else {
            player.sendSystemMessage(Component.translatable("altar.spacerain.need_altar"));
        }


    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(ModItems.FALLEN_SIGIL.get(), ModItems.ASTRAL_JEWEL.get(), ModItems.COBALT_INGOT.get(), ModBlocks.FRIGID_METEORITE_BLOCK.get().asItem(), ModBlocks.MOLTEN_METEORITE_BLOCK.get().asItem());
    }
}
