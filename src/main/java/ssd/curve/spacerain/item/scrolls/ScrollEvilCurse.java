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
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollEvilCurse extends RitualScrollItem {
    public ScrollEvilCurse(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {


        player.hurt(level.damageSources().magic(), 2000);
        //player.setSecondsOnFire(3); nahhhh idk
        if (player.getHealth() > 0) {
            List<ServerPlayer> people = level.getServer().getPlayerList().getPlayers();
            for (ServerPlayer person : people) {
                if (person != player) {
                    person.addEffect(new MobEffectInstance(MobEffects.HUNGER, 72000, 0)); // evil shit
                }
                person.sendSystemMessage(Component.translatable("altar.spacerain.famine"));
            }
        } else {
            player.sendSystemMessage(Component.translatable("altar.spacerain.weakling"));
        }


        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.NETHER_STAR);
    }
}
