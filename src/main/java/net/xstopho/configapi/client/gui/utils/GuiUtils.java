package net.xstopho.configapi.client.gui.utils;

import net.minecraft.client.Minecraft;

public class GuiUtils {
    private static final Minecraft client = Minecraft.getInstance();

    public static int getScaledGuiWidth() {
        return client.getWindow().getGuiScaledWidth();
    }
}
