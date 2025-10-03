package net.xstopho.configapitest;

import net.fabricmc.api.ModInitializer;
import net.xstopho.configapi.api.ConfigRegistry;
import net.xstopho.configapitest.configs.ClientConfig;
import net.xstopho.configapitest.configs.StyleConfig;

public class ConfigApiTest implements ModInitializer {
    public static final String MOD_ID = "config-api-test";

    @Override
    public void onInitialize() {
        ConfigRegistry.registerConfig(ClientConfig.class, MOD_ID);
        ConfigRegistry.registerConfig(StyleConfig.class, MOD_ID);
    }
}
