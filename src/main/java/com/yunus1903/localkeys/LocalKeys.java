package com.yunus1903.localkeys;

import com.yunus1903.localkeys.client.misc.KeyboardLayoutHandler;
import com.yunus1903.localkeys.data.KeyboardLayoutProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main mod class
 * @author Yunus1903
 * @since 07/08/2020
 */
@Mod(LocalKeys.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = LocalKeys.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LocalKeys
{
    public static final String MOD_ID = "localkeys";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static KeyboardLayoutHandler keyboardLayoutHandler;

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event)
    {
        LOGGER.info("Starting!");
        keyboardLayoutHandler = new KeyboardLayoutHandler(event.getMinecraftSupplier().get());
    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        if (event.includeClient())
        {
            generator.addProvider(new KeyboardLayoutProvider(generator));
        }
    }
}