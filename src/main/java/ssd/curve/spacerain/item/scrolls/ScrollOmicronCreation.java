package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollOmicronCreation extends RitualScrollItem {
    public ScrollOmicronCreation(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {
        ItemStack cobalt = new ItemStack(ModItems.COBALT_OMICRON.get(), 1);


        player.hurt(level.damageSources().magic(), 20);
        //player.setSecondsOnFire(3); nahhhh idk
        if (player.getHealth() > 0) {
            player.drop(cobalt, false);
        } else {
            player.sendSystemMessage(Component.translatable("altar.spacerain.weakling"));
        }

        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(ModItems.COBALT_SWORD.get(), ModItems.ASTRAL_JEWEL.get(), ModItems.FALLEN_SIGIL.get());
    }
}
