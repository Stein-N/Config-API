package net.xstopho.configapi.annotations;

import net.xstopho.configapi.api.ConfigRegistry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    String filename();
    ConfigRegistry.Type type();
}
