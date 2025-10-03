package net.xstopho.configapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RangedEntry {
    double min();
    double max();
}
