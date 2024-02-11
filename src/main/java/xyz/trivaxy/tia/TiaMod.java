package xyz.trivaxy.tia;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(TiaMod.MODID)
public class TiaMod
{
    public static final String MODID = "tia";
    public static float carriedAnimationProgress = 0f;

    public TiaMod(IEventBus modEventBus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TiaConfig.CONFIG_SPEC);
    }
}