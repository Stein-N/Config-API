package net.xstopho.configapitest;

import net.fabricmc.api.ModInitializer;
import net.xstopho.configapi.api.ConfigRegistry;
import net.xstopho.configapitest.configs.ClientConfig;

public class ConfigApiTest implements ModInitializer {
    public static final String MOD_ID = "config-api-test";

    @Override
    public void onInitialize() {
        ConfigRegistry.registerConfig(ClientConfig.Gui.class, MOD_ID);
        ConfigRegistry.registerConfig(ClientConfig.Style.class, MOD_ID);
        ConfigRegistry.registerConfig(ClientConfig.Color.class, MOD_ID);
    }
}
