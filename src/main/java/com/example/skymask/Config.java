package com.example.skymask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Config {
    public static boolean enabled = false;
    public static float brightnessThreshold = 0.5f;
    public static boolean maskOnly = false;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File configFile;

    public static void init() {
        configFile = new File(Minecraft.getInstance().gameDirectory, "config/skymask.json");
        load();
    }

    public static void load() {
        if (configFile == null || !configFile.exists()) {
            save();
            return;
        }
        try (FileReader reader = new FileReader(configFile)) {
            ConfigData data = GSON.fromJson(reader, ConfigData.class);
            if (data != null) {
                enabled = data.enabled;
                brightnessThreshold = data.brightnessThreshold;
                maskOnly = data.maskOnly;
            }
        } catch (Exception e) {
            SkyMaskMod.LOGGER.error("Failed to load config", e);
        }
    }

    public static void save() {
        if (configFile == null) return;
        try {
            configFile.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(configFile)) {
                ConfigData data = new ConfigData();
                data.enabled = enabled;
                data.brightnessThreshold = brightnessThreshold;
                data.maskOnly = maskOnly;
                GSON.toJson(data, writer);
            }
        } catch (Exception e) {
            SkyMaskMod.LOGGER.error("Failed to save config", e);
        }
    }

    private static class ConfigData {
        boolean enabled;
        float brightnessThreshold;
        boolean maskOnly;
    }
}
