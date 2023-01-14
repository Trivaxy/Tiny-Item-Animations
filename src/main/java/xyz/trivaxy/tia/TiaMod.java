package xyz.trivaxy.tia;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TiaMod implements ClientModInitializer {
	public static final String MODID = "tia";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static float carriedAnimationProgress = 0f;

	@Override
	public void onInitializeClient() {
		TiaConfig.load();
	}
}
