package net.xstopho.configapi.platform;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface PlatformHelper {
    PlatformHelper INSTANCE = ServiceLoader.load(PlatformHelper.class).findFirst()
            .orElseThrow(() -> new NullPointerException("No PlatformHelper implementation found!"));

    Path getConfigDirectory();
    Path getServerConfigDirectory();
    boolean isServer();
    boolean isDevEnvironment();
}
