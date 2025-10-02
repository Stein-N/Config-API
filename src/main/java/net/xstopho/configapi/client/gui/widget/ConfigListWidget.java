package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

import java.util.Collection;

public class ConfigListWidget extends ContainerObjectSelectionList<ConfigListEntry> {

    public ConfigListWidget(int width, int height, int y, int itemHeight) {
        super(Minecraft.getInstance(), width, height, y, itemHeight);
    }

    @Override
    public void replaceEntries(Collection<ConfigListEntry> collection) {
        super.replaceEntries(collection);
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {}

    @Override
    public int getRowWidth() {
        return (int) Math.floor(this.width * 0.75);
    }
}
