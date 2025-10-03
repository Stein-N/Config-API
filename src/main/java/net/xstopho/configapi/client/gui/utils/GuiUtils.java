package net.xstopho.configapi.client.gui.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

public class GuiUtils {
    private static final Minecraft client = Minecraft.getInstance();

    public static int getScaledGuiWidth() {
        return client.getWindow().getGuiScaledWidth();
    }

    public static Font getFont() {
        return client.font;
    }
}
