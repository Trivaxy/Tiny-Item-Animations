package xyz.trivaxy.tia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class TiaConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("tia.json");
    private static final File CONFIG_FILE = CONFIG_PATH.toFile();

    private static final float defaultAnimationSpeed = 0.5f;
    private static final float defaultPickupScale = 1.4f;

    public static float animationSpeed = defaultAnimationSpeed;
    public static float pickupScale = defaultPickupScale;

    public static void load() {
        if (!CONFIG_FILE.isFile()) {
            TiaMod.LOGGER.info("Config file not found, creating one...");

            try {
                createDefaultConfigFile();
            } catch (IOException e) {
                logError("Could not create config file", e);
                return;
            }
        }

        try {
            JsonObject config = GSON.fromJson(Files.readString(CONFIG_PATH), JsonObject.class);
            animationSpeed = config.get("animation_speed").getAsFloat();
            pickupScale = config.get("pickup_scale").getAsFloat();
        } catch (IOException e) {
            logError("Could not read config file", e);
        } catch (NullPointerException e) {
            logError("Config file is invalid", e);
        }
    }

    private static void createDefaultConfigFile() throws IOException {
        JsonObject config = new JsonObject();
        config.addProperty("animation_speed", defaultAnimationSpeed);
        config.addProperty("pickup_scale", defaultPickupScale);

        BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE, false));
        writer.write(GSON.toJson(config));
        writer.close();
    }

    private static void logError(String message, Exception exception) {
        TiaMod.LOGGER.error(message + ": " + exception.getMessage());
        TiaMod.LOGGER.error("Using default values instead");
    }
}
