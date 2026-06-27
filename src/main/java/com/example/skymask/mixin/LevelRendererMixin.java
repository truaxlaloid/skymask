package com.example.skymask.mixin;

import com.example.skymask.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow 
    @Nullable 
    private VertexBuffer starBuffer;

    @Unique
    private static final ResourceLocation SKYMASK_SUN_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/sun.png");
    @Unique
    private static final ResourceLocation SKYMASK_MOON_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/moon_phases.png");
    @Unique
    private static final ResourceLocation SKYMASK_TRANSPARENT = ResourceLocation.fromNamespaceAndPath("skymask", "textures/environment/transparent.png");

    // Redirect the draw call for VertexBuffers inside renderSky (bypasses drawing the star VBO)
    @Redirect(
        method = "renderSky",
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexBuffer;drawWithShader(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/client/renderer/ShaderInstance;)V")
    )
    private void redirectDrawWithShader(VertexBuffer instance, Matrix4f modelViewMatrix, Matrix4f projectionMatrix, ShaderInstance shaderInstance) {
        if (Config.enabled && instance == this.starBuffer) {
            // Skip drawing the stars (leaves them the sky color)
            return;
        }
        instance.drawWithShader(modelViewMatrix, projectionMatrix, shaderInstance);
    }

    // Redirect shader textures inside renderSky (binds a transparent texture for sun and moon)
    @Redirect(
        method = "renderSky",
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V")
    )
    private void redirectSetShaderTexture(int textureUnit, ResourceLocation textureLocation) {
        if (Config.enabled && (SKYMASK_SUN_LOCATION.equals(textureLocation) || SKYMASK_MOON_LOCATION.equals(textureLocation))) {
            // Bind our 1x1 transparent texture to make them invisible
            RenderSystem.setShaderTexture(textureUnit, SKYMASK_TRANSPARENT);
        } else {
            RenderSystem.setShaderTexture(textureUnit, textureLocation);
        }
    }
}
