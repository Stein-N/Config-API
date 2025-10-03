package net.xstopho.configapitest.configs;

import net.xstopho.configapi.annotations.Config;
import net.xstopho.configapi.annotations.ConfigEntry;
import net.xstopho.configapi.api.ConfigRegistry;

@Config(filename = "gui", type = ConfigRegistry.Type.CLIENT)
public class ClientConfig {

    @ConfigEntry
    public static int scale = 100;

    @ConfigEntry
    public static int powerConsumption = 100;

    @ConfigEntry
    public static int powerExtraction = 100;
}
