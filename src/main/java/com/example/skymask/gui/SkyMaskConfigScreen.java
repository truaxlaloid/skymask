package com.example.skymask.gui;
import com.example.skymask.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SkyMaskConfigScreen extends Screen {
    public SkyMaskConfigScreen() {
        super(Component.literal("Sky Mask Config"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        this.addRenderableWidget(new ThresholdSlider(centerX - 100, centerY - 45, 200, 20, Config.brightnessThreshold, val -> {
            Config.brightnessThreshold = val.floatValue();
            Config.save();
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(Config.enabled)
                .create(centerX - 100, centerY - 15, 200, 20, Component.literal("Enable Mask"), (btn, value) -> {
                    Config.enabled = value;
                    Config.save();
                }));
        this.addRenderableWidget(CycleButton.onOffBuilder(Config.maskOnly)
                .create(centerX - 100, centerY + 15, 200, 20, Component.literal("Mask Only (Black BG)"), (btn, value) -> {
                    Config.maskOnly = value;
                    Config.save();
                }));
        this.addRenderableWidget(Button.builder(Component.literal("Done"), btn -> this.onClose())
                .bounds(centerX - 100, centerY + 45, 200, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
}
