package net.xstopho.configapi.api;

import net.xstopho.configapi.ConfigApi;
import net.xstopho.configapi.annotations.Config;
import net.xstopho.configapi.config.ModConfig;

import java.util.ArrayList;
import java.util.List;

public class ConfigRegistry {

    private static final List<ModConfig> CONFIG_LIST = new ArrayList<>();

    public static void registerConfig(Class<?> clazz, String modId) {
        if (!clazz.isAnnotationPresent(Config.class)) {
            ConfigApi.LOGGER.error("Class '{}' was skipped: Missing Config Annotation!", clazz.getName());
        }

        Config annotation = clazz.getAnnotation(Config.class);

        ModConfig config = new ModConfig(clazz, annotation.type(), modId);
        if (!CONFIG_LIST.contains(config)) CONFIG_LIST.add(config);
    }

    public static List<ModConfig> getConfigList() {
        return CONFIG_LIST;
    }

    public enum Type {
        CLIENT, COMMON, SERVER;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
