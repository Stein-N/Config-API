package net.xstopho.configapitest.configs;

import net.xstopho.configapi.annotations.Config;
import net.xstopho.configapi.annotations.ConfigEntry;
import net.xstopho.configapi.api.ConfigRegistry;

@Config(filename = "style", type = ConfigRegistry.Type.CLIENT)
public class StyleConfig {

    @ConfigEntry
    public static int style = 3;
}
