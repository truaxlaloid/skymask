package com.example.skymask;

import com.example.skymask.gui.ClothConfigScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SkyMaskMod.MODID)
public class SkyMaskMod {
    public static final String MODID = "skymask";
    public static final Logger LOGGER = LoggerFactory.getLogger("SkyMask");

    public SkyMaskMod(ModContainer container, IEventBus modBus) {
        Config.init();

        // Register the Cloth Config Screen as the official NeoForge config screen
        container.registerExtensionPoint(IConfigScreenFactory.class, 
            (mc, parent) -> ClothConfigScreen.createScreen(parent));

        LOGGER.info("Sky Mask Mod Initialized with Cloth Config!");
    }
}
