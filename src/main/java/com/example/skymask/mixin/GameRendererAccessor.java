package com.example.skymask.mixin;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import javax.annotation.Nullable;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor("postEffect")
    @Nullable PostChain getPostEffect();
    @Invoker("loadEffect") void callLoadEffect(ResourceLocation resourceLocation);
    @Invoker("shutdownEffect") void callShutdownEffect();
}
