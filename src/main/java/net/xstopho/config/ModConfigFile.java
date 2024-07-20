package net.xstopho.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.InMemoryCommentedFormat;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.xstopho.builder.IResourceConfigBuilder;
import net.xstopho.config.entry.ConfigEntry;
import net.xstopho.config.values.ConfigValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModConfigFile {

    private final String modId;
    private final File file;
    private final IResourceConfigBuilder builder;
    private final TomlWriter writer = new TomlWriter();
    private final boolean disableRangedComments;

    private CommentedConfig config = CommentedConfig.inMemory();

    public ModConfigFile(String modId, String fileName, IResourceConfigBuilder builder, Path configPath, boolean disableRangedComments) {
        this.file = new File(configPath + "/" + fileName + ".toml");
        this.disableRangedComments = disableRangedComments;
        createFilePathIfNeeded(configPath);
        this.builder = builder;
        this.modId = modId;

        readConfigFile();
        List<ConfigEntry<?>> entries = new ArrayList<>(builder.getEntries().values());
        entries.forEach(this::readConfigValues);

        config = CommentedConfig.of(LinkedHashMap::new, InMemoryCommentedFormat.withUniversalSupport());

        entries.forEach(this::writeConfigValues);

        writeConfigFile();

        entries.forEach(ConfigEntry::setLoaded);
    }

    void readConfigFile() {
        if (!file.exists()) return;
        try (FileReader reader = new FileReader(file)) {
            config = new TomlParser().parse(reader);
        } catch(IOException | ParsingException e) {
            //TODO: add correct Logger
        }
    }

    void writeConfigFile() {
        writeCategoryComments();

        writer.setIndentArrayElementsPredicate(objects -> true);
        writer.write(config, file, WritingMode.REPLACE);
    }

    <T> void readConfigValues(ConfigEntry<T> entry) {
        String path = entry.getPath();
        T defaultValue = entry.getConfigValue().get();

        if (!config.contains(path) || !entry.getConfigValue().isValid(config.get(path))) {
            entry.setValue(defaultValue);
            //TODO: add correct Logger
            //ResourceConstants.LOG.error("Config Entry key '{}' isn't correct and is set to its default value '{}'!", path, defaultValue);
        } else entry.setValue(config.get(path));
    }

    void writeConfigValues(ConfigEntry<?> entry) {
        writeValueComment(entry);
        config.set(entry.getPath(), entry.value());
    }

    void writeValueComment(ConfigEntry<?> entry) {
        ConfigValue<?> value = entry.getConfigValue();

        if (value.isRanged() && !disableRangedComments) config.setComment(entry.getPath(), value.getRangedComment());
        else if (value.hasComment()) config.setComment(entry.getPath(), value.getComment());
    }

    void writeCategoryComments() {
        for (Map.Entry<String, String> comment : builder.getCategoryComment().entrySet()) {
            config.setComment(comment.getKey(), comment.getValue());
        }
    }

    void createFilePathIfNeeded(Path path) {
        if (new File(path.toString()).mkdirs()) {
            //TODO: add correct Logger
            //ResourceConstants.LOG.info("Created custom Config Path: '{}' for Mod: '{}'", path, modId);
        }
    }

    public IResourceConfigBuilder getBuilder() {
        return builder;
    }
}
