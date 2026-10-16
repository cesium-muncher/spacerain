package ssd.curve.spacerain.item.scrolls;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.valkyrienskies.mod.api.ValkyrienSkies;
import ssd.curve.spacerain.item.custom.RitualScrollItem;

import java.util.List;

public class ScrollFireBall extends RitualScrollItem {
    public ScrollFireBall(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {

        Entity entity = EntityType.FIREBALL.create(level);
        entity.moveTo(player.getEyePosition());
        entity.addDeltaMovement(player.getLookAngle());
        level.addFreshEntity(entity);


        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(Items.FIRE_CHARGE);
    }
}
