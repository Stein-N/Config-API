package net.xstopho.api;

import net.xstopho.builder.IResourceConfigBuilder;
import net.xstopho.config.ModConfigFile;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ConfigRegistry {

    public static final List<ModConfigFile> MOD_CONFIG_FILES = new LinkedList<>();

    public static void register(String modId, String fileName, IResourceConfigBuilder builder, Path path, boolean disableRangedComments) {
        MOD_CONFIG_FILES.add(new ModConfigFile(modId, fileName, builder, path, disableRangedComments));
    }

    public static void register(String modId, IResourceConfigBuilder builder, Path path, boolean disableRangedComments) {
        MOD_CONFIG_FILES.add(new ModConfigFile(modId, modId, builder, path, disableRangedComments));
    }

//    public static void register(String modId, String fileName, IResourceConfigBuilder builder, boolean disableRangedComments) {
//        MOD_CONFIG_FILES.add(new ModConfigFile(modId, fileName, builder, Services.CONFIG_DIR, disableRangedComments));
//    }

//    public static void register(String modId, IResourceConfigBuilder builder, boolean disableRangedComments) {
//        MOD_CONFIG_FILES.add(new ModConfigFile(modId, modId, builder, Services.CONFIG_DIR, disableRangedComments));
//    }

}
