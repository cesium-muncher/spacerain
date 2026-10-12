package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;
import java.util.Random;

public class ScrollGlobalWarming extends RitualScrollItem {
    public ScrollGlobalWarming(Properties pProperties) {
        super(pProperties);
    }
    private static final int radius = 5;
    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {


        player.hurt(level.damageSources().magic(), 2000);
        //player.setSecondsOnFire(3); nahhhh idk
        if (player.getHealth() > 0) {
            ominoussfx(level, pos);
            lightning(level, pos);
            List<ServerPlayer> people = level.getServer().getPlayerList().getPlayers();
            for (ServerPlayer person : people) {
                if (person != player) {
                    person.setSecondsOnFire(3600);

                }
                for (int x = -radius; x<= radius; x++) {
                    for (int y = -radius; y<= radius; y++) {
                        for (int z = -radius; z<= radius; z++) {
                            if (Math.random() > 0.8f && Math.sqrt(x*x + y*y + z*z) <=radius) {
                                person.blockPosition();

                                BlockPos newpos = new BlockPos(person.blockPosition().getX() + x, person.blockPosition().getY() + y, person.blockPosition().getZ() + z);
                                BlockState block = level.getBlockState(newpos);
                                if (block != null) {
                                    if (block.isAir() || block.canBeReplaced()) {
                                        if (person.level().getBlockState(newpos.below()).isSolid()) {
                                            person.level().setBlockAndUpdate(newpos, Blocks.FIRE.defaultBlockState());
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                person.sendSystemMessage(Component.translatable("altar.spacerain.global_warming"));
            }

        } else {
            player.sendSystemMessage(Component.translatable("altar.spacerain.weakling"));
        }


        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(ModItems.AWAKENED_SIGIL.get(), Items.BLAZE_ROD, Items.FIRE_CHARGE, Items.FIRE_CORAL, ModItems.ASTRAL_JEWEL.get());
    }
}
