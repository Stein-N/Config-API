package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

import java.util.List;

public class ConfigListWidget extends ContainerObjectSelectionList<ConfigListEntry> {

    public ConfigListWidget(int width, int height, int yPos, int defaultEntryHeight, List<ConfigListEntry> entries) {
        super(Minecraft.getInstance(), width, height, yPos, defaultEntryHeight);
        entries.forEach(this::addEntry);
    }

    @Override
    public int getRowWidth() {
        return (int) Math.floor(this.width * 0.8);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderWidget(guiGraphics, i, j, f);
        this.repositionEntries();
    }
}
