package xyz.trivaxy.tia;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(TiaMod.MODID)
public class TiaMod
{
    public static final String MODID = "tia";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static float carriedAnimationProgress = 0f;

    public TiaMod()
    {
        if (FMLEnvironment.dist.isDedicatedServer()) {
            LOGGER.info("TIA is a client-side mod and cannot be loaded on a dedicated server.");
            return;
        }

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TiaConfig.SPEC, MODID + ".toml");
    }

    @SubscribeEvent
    public void onItemSlotStack(ItemStackedOnOtherEvent event) {
        Animated slot = (Animated) event.getSlot();
        slot.setAnimationProgress(1f);
        carriedAnimationProgress = 0f;
    }
}
