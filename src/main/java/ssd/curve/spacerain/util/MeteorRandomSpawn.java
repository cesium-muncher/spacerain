package ssd.curve.spacerain.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;
import ssd.curve.spacerain.Spacerain;

// ai slop code from google overview cuz idfk ts

@Mod.EventBusSubscriber(modid = Spacerain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MeteorRandomSpawn {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;


            if (!player.level().isClientSide()) {
                //ok here is not ai code


                // multiply percentage by 1000 for number or smthn
                // default is 2 aka 0.002% per tick
                // 3% over the span of a minute
                // 76% over an hour
                // math type shit

                if (player.level().dimension() == Level.OVERWORLD) { // todo for later, add config for all dimensions n shit
                    if (Math.random() * 100000 < 2) {
                        MeteorGenerator mg = new MeteorGenerator();
                        mg.generateMeteorForRealThisTimeISwear(player);
                        player.sendSystemMessage(Component.literal("debug: meteor is falling, yell at mod dev to remove this!!!!"));
                    }
                }

            }
        }
    }

}
