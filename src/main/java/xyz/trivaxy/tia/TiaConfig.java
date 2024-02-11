package xyz.trivaxy.tia;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@Mod.EventBusSubscriber(modid = TiaMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiaConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue ANIMATION_SPEED = BUILDER
            .comment("The speed at which item pickup / insert animations play")
            .defineInRange("animation_speed", 0.5, 0.01, 10);

    private static final ModConfigSpec.DoubleValue PICKUP_SCALE = BUILDER
            .comment("The scale at which items are rendered when picked up")
            .defineInRange("pickup_scale", 1.4, 0.01, 10);

    public static final ModConfigSpec CONFIG_SPEC = BUILDER.build();

    public static float animationSpeed;
    public static float pickupScale;

    @SubscribeEvent
    private static void onLoad(final ModConfigEvent event) {
        animationSpeed = ANIMATION_SPEED.get().floatValue();
        pickupScale = PICKUP_SCALE.get().floatValue();
    }
}