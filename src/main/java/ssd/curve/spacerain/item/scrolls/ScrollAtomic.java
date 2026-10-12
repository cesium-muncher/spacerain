package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollAtomic extends RitualScrollItem {
    public ScrollAtomic(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {

        Player target = level.getNearestPlayer(TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting(), pos.getX(), pos.getY(), pos.getZ());
        if (target != null) {
            if (target.position().distanceTo(pos.getCenter()) < 3) {
                if (!target.equals(player)) {
                    Entity entity = EntityType.TNT.create(level);
                    entity.moveTo(BlockPos.containing(target.position()), 0, 0);
                    level.addFreshEntity(entity);
                } else {
                    refundRitual(player);
                    player.sendSystemMessage(Component.translatable("altar.spacerain.atomic_self"));
                }

            } else {
                refundRitual(player);
                player.sendSystemMessage(Component.translatable("altar.spacerain.notarget"));
            }
        } else {
            refundRitual(player);
            player.sendSystemMessage(Component.translatable("altar.spacerain.notarget"));
        }



        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.FIRE_CHARGE, Items.TNT);
    }
}
