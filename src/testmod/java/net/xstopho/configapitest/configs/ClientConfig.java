package net.xstopho.configapitest.configs;

import net.xstopho.configapi.annotations.Config;
import net.xstopho.configapi.annotations.ConfigEntry;
import net.xstopho.configapi.api.ConfigRegistry;

public class ClientConfig {

    @Config(filename = "gui", type = ConfigRegistry.Type.CLIENT)
    public static class Gui {
        @ConfigEntry
        public static int scale = 100;
        @ConfigEntry
        public static int powerConsumption = 100;
        @ConfigEntry
        public static int powerExtraction = 100;
    }

    @Config(filename = "style", type = ConfigRegistry.Type.CLIENT)
    public static class Style {
        @ConfigEntry
        public static int scale = 100;
        @ConfigEntry
        public static String message = "Test String";
        @ConfigEntry
        public static String login = "Login ";
    }

    @Config(filename = "color", type = ConfigRegistry.Type.CLIENT)
    public static class Color {
        @ConfigEntry
        public static int primary = 512346789;
        @ConfigEntry
        public static int secondary = 253416798;
    }
}
