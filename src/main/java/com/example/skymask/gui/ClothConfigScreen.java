package com.example.skymask.gui;

import com.example.skymask.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigScreen {
    public static Screen createScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Sky Mask Configuration"));

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // 1. Enable Toggle
        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Mask"), Config.enabled)
                .setDefaultValue(false)
                .setSaveConsumer(val -> Config.enabled = val)
                .build());

        // 2. Threshold Slider (0% - 100%)
        general.addEntry(entryBuilder.startIntSlider(Component.literal("Brightness Threshold (%)"), (int) (Config.brightnessThreshold * 100), 0, 100)
                .setDefaultValue(50)
                .setSaveConsumer(val -> Config.brightnessThreshold = val / 100.0f)
                .build());

        // 3. Mask Only Mode (Black background vs transparent overlay)
        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Mask Only (Black BG)"), Config.maskOnly)
                .setDefaultValue(false)
                .setSaveConsumer(val -> Config.maskOnly = val)
                .build());

        builder.setSavingRunnable(Config::save);

        return builder.build();
    }
}
