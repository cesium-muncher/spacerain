package ssd.curve.spacerain.item.scrolls;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.api.ValkyrienSkies;
import ssd.curve.spacerain.Spacerain;
import ssd.curve.spacerain.block.ModBlocks;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.item.custom.RitualScrollItem;
import ssd.curve.spacerain.util.ModDamageSources;

import javax.annotation.Nullable;
import java.util.List;

public class ScrollBlight extends RitualScrollItem {
    public ScrollBlight(Properties pProperties) {
        super(pProperties);
    }
    private static final int radius = 5;
    @Override
    public void performRitual(Level level, BlockPos pos, Player player) {


        player.hurt(ModDamageSources.causeDmg(level, null, ModDamageSources.UNWORTHY), 2000);
        //player.setSecondsOnFire(3); nahhhh idk
        if (player.getHealth() > 0) {
            ominoussfx(level, pos);
            lightning(level, pos);
            List<ServerPlayer> people = level.getServer().getPlayerList().getPlayers();
            for (ServerPlayer person : people) {
                person.sendSystemMessage(Component.translatable("altar.spacerain.blight"));
            }
            level.setBlockAndUpdate(pos, ModBlocks.STELLAR_BLIGHT.get().defaultBlockState());
            level.getServer().overworld().setWeatherParameters(0, 72000, true, true);
            Advancement adv = player.getServer().getAdvancements().getAdvancement(new ResourceLocation(Spacerain.MODID, "blight_spawn"));
            for (String criterion : adv.getCriteria().keySet()) {
                ((ServerPlayer) player).getAdvancements().award(adv, criterion);
            }
            @Nullable Ship ship = ValkyrienSkies.getShipManagingBlock(level, pos);
            if (ship != null) {
                Advancement adv2 = player.getServer().getAdvancements().getAdvancement(new ResourceLocation(Spacerain.MODID, "blight_spawn_ship"));
                for (String criterion : adv2.getCriteria().keySet()) {
                    ((ServerPlayer) player).getAdvancements().award(adv2, criterion);
                }
            }
            if (level.getBiome(pos).is(BiomeTags.IS_OCEAN) || level.getBiome(pos).is(Tags.Biomes.IS_COLD)) {
                Advancement adv2 = player.getServer().getAdvancements().getAdvancement(new ResourceLocation(Spacerain.MODID, "blight_spawn_cold"));
                for (String criterion : adv2.getCriteria().keySet()) {
                    ((ServerPlayer) player).getAdvancements().award(adv2, criterion);
                }
            }
        } else {
            player.sendSystemMessage(Component.translatable("altar.spacerain.weakling"));
        }


        //super.performRitual(level, pos, player);
    }

    @Override
    public List<Item> getItemRequirements() {
        return List.of(ModItems.AWAKENED_SIGIL.get(), ModItems.ASTRAL_JEWEL.get(), Items.NETHER_STAR, Items.NETHERITE_INGOT);
    }
}
