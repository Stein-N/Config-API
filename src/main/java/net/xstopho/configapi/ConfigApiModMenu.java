package net.xstopho.configapi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.xstopho.configapi.api.ConfigRegistry;
import net.xstopho.configapi.client.gui.ConfigApiScreen;

import java.util.HashMap;
import java.util.Map;

public class ConfigApiModMenu implements ModMenuApi {
    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> screenFactoryMap = new HashMap<>();

        ConfigRegistry.getConfigList().forEach(config -> {
            screenFactoryMap.putIfAbsent(config.getModId(), screen -> new ConfigApiScreen(screen, config.getModId()));
        });

        return screenFactoryMap;
    }
}
