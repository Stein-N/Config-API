package net.xstopho.configapi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.configapi.client.gui.screen.ConfigApiScreen;
import org.lwjgl.glfw.GLFW;

public class ConfigApiClient implements ClientModInitializer {

    final KeyMapping.Category CATEGORY = new KeyMapping.Category(ResourceLocation.fromNamespaceAndPath(ConfigApi.MOD_ID, "config_screen"));
    final KeyMapping SHOW = new KeyMapping(ConfigApi.MOD_ID + ".key.show", GLFW.GLFW_KEY_H, CATEGORY);

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(SHOW);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (SHOW.consumeClick()) {
                client.setScreen(new ConfigApiScreen(client.screen, ConfigApi.MOD_ID));
            }
        });
    }
}
