package net.xstopho.configapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigEntry {
    String category() default "";
    boolean gameRestart() default false;
    boolean worldRestart() default false;
    String translation() default "";
}
