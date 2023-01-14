package xyz.trivaxy.tia;

import net.minecraftforge.common.ForgeConfigSpec;

public class TiaConfig {
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.DoubleValue animationSpeed;
    public static ForgeConfigSpec.DoubleValue pickupScale;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        setupConfig(builder);
        SPEC = builder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Configuration settings for Tia");
        builder.push("General options");

        animationSpeed = builder
                .comment("The speed at which item pickup / insert animations play")
                .defineInRange("animation_speed", 0.5, 0.01, 10);

        pickupScale = builder
                .comment("The scale at which items are rendered when picked up")
                .defineInRange("pickup_scale", 1.4, 0.01, 10);

        builder.pop();
    }
}
