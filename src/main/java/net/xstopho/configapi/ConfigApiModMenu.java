package net.xstopho.configapi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.xstopho.configapi.client.gui.screen.ConfigApiScreen;

public class ConfigApiModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> new ConfigApiScreen(screen, ConfigApi.MOD_ID);
    }
}
