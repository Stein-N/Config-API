package net.xstopho.config.entry;

import net.xstopho.config.values.ConfigValue;

public class ConfigEntry<T> {
    protected final String path;
    protected final ConfigValue<T> configValue;
    protected final boolean sync;

    protected boolean isLoaded;
    private T value;


    public ConfigEntry(String path, ConfigValue<T> configValue, boolean sync) {
        this.path = path;
        this.configValue = configValue;
        this.sync = sync;
    }

    public T getValue() {
        if (!isLoaded) throw new IllegalStateException("Config isn't loaded yet!");
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public ConfigValue<T> getConfigValue() {
        return configValue;
    }

    public boolean syncWithServer() {
        return sync;
    }

    public void setLoaded() {
        isLoaded = true;
    }

    public T value() {
        return value;
    }
}
