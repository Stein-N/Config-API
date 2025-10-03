package net.xstopho.configapitest;

import net.fabricmc.api.ModInitializer;
import net.xstopho.configapi.api.ConfigRegistry;
import net.xstopho.configapitest.configs.ClientConfig;

public class ConfigApiTest implements ModInitializer {
    public static final String MOD_ID = "configapitest";

    @Override
    public void onInitialize() {
        ConfigRegistry.registerConfig(ClientConfig.class, MOD_ID);
    }
}
