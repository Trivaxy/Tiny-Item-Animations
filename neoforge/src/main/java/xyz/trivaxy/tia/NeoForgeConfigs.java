package xyz.trivaxy.tia;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Commons.MOD_ID)
public class NeoForgeConfigs {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue ANIMATION_SPEED = BUILDER
        .comment("The speed at which item pickup / insert animations play")
        .defineInRange("animation_speed", ModConfigs.getDefaultAnimationSpeed(), 0.01, 10);

    private static final ModConfigSpec.DoubleValue PICKUP_SCALE = BUILDER
        .comment("The scale at which items are rendered when picked up")
        .defineInRange("pickup_scale", ModConfigs.getDefaultPickupScale(), 0.01, 10);

    public static final ModConfigSpec CONFIG_SPEC = BUILDER.build();

    @SubscribeEvent
    private static void onLoad(final ModConfigEvent event) {
        ModConfigs.animationSpeed = ANIMATION_SPEED.get().floatValue();
        ModConfigs.pickupScale = PICKUP_SCALE.get().floatValue();
    }
}