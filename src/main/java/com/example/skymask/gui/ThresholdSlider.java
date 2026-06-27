package com.example.skymask.gui;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import java.util.function.Consumer;

public class ThresholdSlider extends AbstractSliderButton {
    private final Consumer<Double> onChange;
    public ThresholdSlider(int x, int y, int width, int height, double value, Consumer<Double> onChange) {
        super(x, y, width, height, Component.literal("Threshold: " + String.format("%.2f", value)), value);
        this.onChange = onChange;
    }
    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal("Threshold: " + String.format("%.2f", this.value)));
    }
    @Override
    protected void applyValue() {
        this.onChange.accept(this.value);
    }
}
