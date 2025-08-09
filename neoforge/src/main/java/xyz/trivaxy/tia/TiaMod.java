package xyz.trivaxy.tia;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Commons.MOD_ID)
public class TiaMod {

    public TiaMod(IEventBus eventBus) {
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, NeoForgeConfigs.CONFIG_SPEC);
    }
}