package com.example.skymask;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SkyMaskMod.MODID)
public class SkyMaskMod {
    public static final String MODID = "skymask";
    public static final Logger LOGGER = LoggerFactory.getLogger("SkyMask");
    public SkyMaskMod(ModContainer container) {
        Config.init();
        LOGGER.info("Sky Mask Mod Initialized!");
    }
}
