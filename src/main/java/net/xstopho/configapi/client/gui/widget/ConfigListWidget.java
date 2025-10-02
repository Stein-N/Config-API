package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

public class ConfigListWidget extends ContainerObjectSelectionList<ConfigListEntry> {

    public ConfigListWidget(int width, int height, int y, int itemHeight) {
        super(Minecraft.getInstance(), width, height, y, itemHeight);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {}

    @Override
    public int getRowWidth() {
        return (int) Math.floor(this.width * 0.75);
    }
}
