package xyz.trivaxy.tia;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(TiaMod.MODID)
public class TiaMod
{
    public static final String MODID = "tia";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static float carriedAnimationProgress = 0f;

    public TiaMod()
    {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TiaConfig.SPEC, MODID + ".toml");
    }
}
