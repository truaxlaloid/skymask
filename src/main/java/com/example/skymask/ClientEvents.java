package com.example.skymask;
import com.example.skymask.gui.SkyMaskConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = SkyMaskMod.MODID, value = Dist.CLIENT)
public class ClientEvents {
    public static final KeyMapping OPEN_CONFIG_KEY = new KeyMapping(
            "key.skymask.open_config",
            GLFW.GLFW_KEY_O,
            "key.categories.misc"
    );

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.player != null) {
            SkyMaskManager.tick(mc);
            while (OPEN_CONFIG_KEY.consumeClick()) {
                mc.setScreen(new SkyMaskConfigScreen());
            }
        }
    }
}
