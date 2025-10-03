package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

public class ConfigListWidget extends ContainerObjectSelectionList<ConfigListEntry> {

    public ConfigListWidget(int width, int height, int yPos, int defaultEntryHeight) {
        super(Minecraft.getInstance(), width, height, yPos, defaultEntryHeight);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {}

    @Override
    protected void renderListBackground(GuiGraphics guiGraphics) {}

    @Override
    public int getRowWidth() {
        return (int) Math.floor(this.width * 0.8);
    }
}
