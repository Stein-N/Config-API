package net.xstopho.configapi.api;

import net.minecraft.resources.ResourceLocation;
import net.xstopho.configapi.ConfigApi;
import net.xstopho.configapi.annotations.Config;
import net.xstopho.configapi.config.ModConfig;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConfigRegistry {

    private static final Map<ResourceLocation, ModConfig> CONFIGS = new HashMap<>();

    public static void registerConfig(Class<?> clazz, String modId) {
        if (!clazz.isAnnotationPresent(Config.class)) {
            ConfigApi.LOGGER.error("Class '{}' was skipped: Missing Config Annotation!", clazz.getName());
        }

        Config config = clazz.getAnnotation(Config.class);
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(modId, config.type().toString().toLowerCase(Locale.ENGLISH) + "/" + config.filename());

        CONFIGS.putIfAbsent(location, new ModConfig(clazz, config.type(), modId));
    }

    public static Map<ResourceLocation, ModConfig> getConfigs() {
        return CONFIGS;
    }

    public enum Type {
        CLIENT, COMMON, SERVER;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
