package com.yunus1903.localkeys.client.misc;

import com.yunus1903.localkeys.LocalKeys;
import net.minecraft.client.gui.screen.ControlsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * @author Yunus1903
 * @since 07/08/2020
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = LocalKeys.MOD_ID)
public class ClientEventHandler
{
    @SubscribeEvent
    public static void onGuiInit(GuiScreenEvent.InitGuiEvent event)
    {
        if (event.getGui() instanceof ControlsScreen)
        {
            KeyboardLayoutHandler keyboardLayoutHandler = LocalKeys.keyboardLayoutHandler;
            event.addWidget(new Button(event.getGui().width / 2 - 208, event.getGui().height - 29, 50, 20, new StringTextComponent(keyboardLayoutHandler.currentLayout.getName()), button ->
            {
                List<KeyboardLayout> layouts = keyboardLayoutHandler.getLayouts();
                int x = layouts.indexOf(keyboardLayoutHandler.currentLayout) + 1;
                x = x >= layouts.size() ? 0 : x;
                keyboardLayoutHandler.remapKeys(layouts.get(x));
                button.setMessage(new StringTextComponent(keyboardLayoutHandler.currentLayout.getName()));
            }));
        }
    }
}
