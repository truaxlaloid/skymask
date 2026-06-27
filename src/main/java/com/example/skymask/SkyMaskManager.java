package com.example.skymask;
import com.example.skymask.mixin.GameRendererAccessor;
import com.example.skymask.mixin.PostChainAccessor;
import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;

public class SkyMaskManager {
    private static boolean loaded = false;
    private static final ResourceLocation SHADER_PATH = ResourceLocation.fromNamespaceAndPath(SkyMaskMod.MODID, "shaders/post/sky_mask.json");

    public static void tick(Minecraft mc) {
        if (mc.level == null || mc.player == null) {
            if (loaded) disableShader(mc);
            return;
        }
        PostChain active = ((GameRendererAccessor) mc.gameRenderer).getPostEffect();
        boolean isOursActive = (active != null && (SkyMaskMod.MODID + ":shaders/post/sky_mask.json").equals(((PostChainAccessor) active).getName()));

        if (Config.enabled) {
            if (!isOursActive) enableShader(mc);
            updateUniforms(mc);
        } else if (isOursActive) {
            disableShader(mc);
        }
    }

    private static void enableShader(Minecraft mc) {
        try {
            ((GameRendererAccessor) mc.gameRenderer).callLoadEffect(SHADER_PATH);
            loaded = true;
        } catch (Exception e) {
            SkyMaskMod.LOGGER.error("Failed to load sky mask shader", e);
        }
    }

    private static void disableShader(Minecraft mc) {
        try {
            ((GameRendererAccessor) mc.gameRenderer).callShutdownEffect();
            loaded = false;
        } catch (Exception e) {
            SkyMaskMod.LOGGER.error("Failed to disable sky mask shader", e);
        }
    }

    private static void updateUniforms(Minecraft mc) {
        PostChain effect = ((GameRendererAccessor) mc.gameRenderer).getPostEffect();
        if (effect != null) {
            for (PostPass pass : ((PostChainAccessor) effect).getPasses()) {
                EffectInstance shader = pass.getEffect();
                Uniform thresholdUniform = shader.getUniform("Threshold");
                if (thresholdUniform != null) thresholdUniform.set(Config.brightnessThreshold);
                Uniform maskOnlyUniform = shader.getUniform("MaskOnly");
                if (maskOnlyUniform != null) maskOnlyUniform.set(Config.maskOnly ? 1.0f : 0.0f);
            }
        }
    }
}
