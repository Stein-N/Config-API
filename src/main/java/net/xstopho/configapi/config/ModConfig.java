package net.xstopho.configapi.config;

import com.google.gson.*;
import net.xstopho.configapi.ConfigApi;
import net.xstopho.configapi.annotations.Config;
import net.xstopho.configapi.annotations.ConfigEntry;
import net.xstopho.configapi.annotations.RangedEntry;
import net.xstopho.configapi.api.ConfigRegistry;
import net.xstopho.configapi.platform.PlatformHelper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModConfig {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final PlatformHelper helper = PlatformHelper.INSTANCE;
    public final ConfigRegistry.Type configType;
    public final String modId;
    private final File file;

    private final Map<Field, Object> defaultValueMap;
    private final Class<?> clazz;

    public ModConfig(Class<?> clazz, ConfigRegistry.Type configType, String modId) {
        this.configType = configType;
        this.clazz = clazz;
        this.modId = modId;

        Path path;
        if (helper.isServer() && configType == ConfigRegistry.Type.SERVER) {
            path = helper.getServerConfigDirectory();
        } else {
            path = helper.getConfigDirectory();
        }

        this.file = new File(String.format("%s/%s/%s/%s.json", path, modId,
                configType.toString(), clazz.getAnnotation(Config.class).filename()));

        this.defaultValueMap = getDefaultValues();

        setupConfig();
    }

    private void setupConfig() {
        this.load();

        if (helper.isServer() != (configType == ConfigRegistry.Type.SERVER)) {
            ConfigApi.LOGGER.info("Config '{}' with type '{}' was skipped!", file.getName(), configType);
            return;
        }

        this.save();
    }

    public JsonObject toJson() {
        JsonObject config = new JsonObject();

        for (Map.Entry<Field, ConfigEntry> entry : getConfigEntries().entrySet()) {
            ConfigEntry configEntry = entry.getValue();
            Field field = entry.getKey();

            String category = configEntry.category().isBlank() ? null : configEntry.category();

            try {
                Object value = field.get(null);
                if (value instanceof Collection<?> || value instanceof Map<?, ?>) {
                    ConfigApi.LOGGER.error("Value '{}' was skipped: Value is an unsupported Datatype", field.getName());
                    continue;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to read Value: " + field.getName(), e);
            }

            JsonObject jsonObject = null;
            if (category != null && config.has(category)) {
                jsonObject = config.getAsJsonObject(category);
            } else if (category != null) {
                jsonObject = new JsonObject();
            }

            String value = field.getName();
            Object obj;
            try {
                field.setAccessible(true);
                obj = field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to read Value: " + field.getName(), e);
            }

            JsonElement jsonElement = gson.toJsonTree(obj);

            if (jsonObject != null) {
                jsonObject.add(value, jsonElement);
                config.add(category, jsonObject);
            } else {
                config.add(value, jsonElement);
            }
        }

        return config;
    }

    public void fromJson(JsonObject config) {
        for (Map.Entry<Field, ConfigEntry> entry : getConfigEntries().entrySet()) {
            ConfigEntry configEntry = entry.getValue();
            Field field = entry.getKey();

            String category = configEntry.category().isBlank() ? null : configEntry.category();

            JsonObject jsonObject = null;
            if (category != null && config.has(category)) {
                jsonObject = config.getAsJsonObject(category);
            }

            String value = field.getName();
            JsonElement jsonElement = jsonObject != null ? jsonObject.get(value) : config.get(value);

            if (jsonElement == null) {
                if (helper.isDevEnvironment()) {
                    ConfigApi.LOGGER.info("Failed to set Value '{}': Might be a new Value.", value);
                }
                continue;
            }

            try {
                Object obj = readValue(jsonElement, field);
                field.set(null, obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T readValue(JsonElement jsonElement, Field field) {
        T defaultValue = (T) defaultValueMap.get(field);
        T parsedValue;
        try {
            parsedValue = gson.fromJson(jsonElement, field.getGenericType());
        } catch (JsonSyntaxException e) {
            ConfigApi.LOGGER.warn("Failed to parse Value for '{}', using default Value: {}", field.getName(), defaultValue);
            return defaultValue;
        }

        if (parsedValue == null) return defaultValue;

        if (field.isAnnotationPresent(RangedEntry.class)) {
            if (parsedValue instanceof Number number) {
                RangedEntry range = field.getAnnotation(RangedEntry.class);

                if (number.doubleValue() >= range.max() && number.doubleValue() <= range.min()) {
                    ConfigApi.LOGGER.warn("Value '{}' is out of range, using default Value: {}", field.getName(), defaultValue);
                    return defaultValue;
                }
            }
        } else {
            ConfigApi.LOGGER.warn("Range for '{}' was ignored, it isn't a Numeric Value!", field.getName());
        }

        return parsedValue;
    }

    private JsonObject readConfig() {
        if (!this.file.exists()) return this.toJson();

        try (FileReader reader = new FileReader(this.file)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config: " + file.getName(), e);
        }
    }

    private void writeConfig(JsonObject config) {
        file.getParentFile().mkdirs();
        try {
            FileUtils.write(file, gson.toJson(config), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write config: " + file.getName(), e);
        }
    }

    private Map<Field, Object> getDefaultValues() {
        Map<Field, Object> map = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            try {
                map.put(field, field.get(null));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Something went wrong while getting default Values for Config: " + clazz.getAnnotation(Config.class).filename(), e);
            }
        }

        return map;
    }

    private Map<Field, ConfigEntry> getConfigEntries() {
        Map<Field, ConfigEntry> map = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) {
                ConfigApi.LOGGER.info("Value '{}' was skipped: ConfigEntry Annotation is missing!", field.getName());
                continue;
            }

            if (!Modifier.isStatic(field.getModifiers())) {
                ConfigApi.LOGGER.info("Value '{}' was skipped: Value isn't static", field.getName());
                continue;
            }

            if (Modifier.isFinal(field.getModifiers())) {
                ConfigApi.LOGGER.info("Value '{}' was skipped: Value is final", field.getName());
                continue;
            }

            ConfigEntry entry = field.getAnnotation(ConfigEntry.class);
            map.put(field, entry);
        }

        return map;
    }

    public Object getDefaultValue(Field field) {
        if (defaultValueMap.containsKey(field)) {
            return defaultValueMap.get(field);
        }
        throw new IllegalStateException("Default Value for Field " + field.getName() + " didn't exist!");
    }

    public void load() {
        this.fromJson(this.readConfig());
    }

    public void save() {
        this.writeConfig(this.toJson());
    }
}