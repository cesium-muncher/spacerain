package ssd.curve.spacerain;

import com.mojang.logging.LogUtils;
import kotlin.jvm.JvmClassMappingKt;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;
import ssd.curve.spacerain.block.ModBlocks;
import ssd.curve.spacerain.block.entity.ModBlockEntities;
import ssd.curve.spacerain.client.ModMenuTypes;
import ssd.curve.spacerain.client.SupernovaData;
import ssd.curve.spacerain.client.SupernovaRenderer;
import ssd.curve.spacerain.forgepackets.ModChannel;
import ssd.curve.spacerain.item.ModCreativeModeTabs;
import ssd.curve.spacerain.item.ModItems;
import ssd.curve.spacerain.network.SupernovaRenderPacket;
import ssd.curve.spacerain.sound.ModSounds;
import ssd.curve.spacerain.util.GradualCraterGenerator;
import ssd.curve.spacerain.util.MeteorImpactCraterManager;
import ssd.curve.spacerain.util.MeteorListenerAttachment;

import java.util.ArrayList;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Spacerain.MODID)
@Mod.EventBusSubscriber
public class Spacerain {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "spacerain";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final List<Long> ListOfMeteorIds = new ArrayList<>();


    public Spacerain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();



        ModItems.register(modEventBus);
        ModChannel.register();
        ModBlockEntities.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModSounds.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ValkyrienSkiesMod.getApi().registerAttachment(MeteorListenerAttachment.class);

        ValkyrienSkiesMod.getApi().getShipLoadEvent().on((event) -> {
            if (ListOfMeteorIds.contains(event.getShip().getId())) {
                ListOfMeteorIds.remove(event.getShip().getId());
                event.getShip().setAttachment(new MeteorListenerAttachment());

            }
        });
        /*ValkyrienSkiesMod.getVsCore().getSimplePacketNetworking().register(
                JvmClassMappingKt.getKotlinClass(SupernovaRenderPacket.class)

        );
        ValkyrienSkiesMod.getVsCore().getSimplePacketNetworking().registerClientHandler(
                JvmClassMappingKt.getKotlinClass(SupernovaRenderPacket.class),
                (packet) -> {
                    SupernovaRenderer.SupernovasToRender.add(new SupernovaData(packet.where(), packet.radius()));
                    return null;
                }
        );*/

        //FIX THIS SHIT WHEN VS UPDATES THEIR PACKET API TO WORK FOR JAVA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        modEventBus.addListener(this::commonSetup);



        MinecraftForge.EVENT_BUS.register(this);

        //modEventBus.addListener(this::onServerTick);
        MinecraftForge.EVENT_BUS.addListener(this::onServerTick);


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);


        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // Add the example block item to the building blocks tab


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        //if (event.phase == TickEvent.Phase.START) {
        //System.out.println("mcburger, new zealand");
        MeteorImpactCraterManager.checkChanges(event);
        MeteorImpactCraterManager.renderparticles(event);
        GradualCraterGenerator.gcd_tickevent(event);
        //}
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        // the bob
        SupernovaRenderer.tickevent(event);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
