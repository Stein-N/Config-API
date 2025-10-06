package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.xstopho.configapi.config.ModConfig;

import java.util.List;

public class ConfigListWidget extends ContainerObjectSelectionList<ConfigListEntry> {
    public ConfigListWidget(int width, int height, int yPos, List<ModConfig> configs) {
        super(Minecraft.getInstance(), width, height, yPos, 24);

        configs.forEach(this::createEntries);
    }

    @Override
    public int getRowWidth() {
        return (int) (this.width * 0.8);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        this.repositionEntries();
        this.refreshScrollAmount();
    }

    private void createEntries(ModConfig config) {
        ConfigListEntry entry = new ConfigListEntry(config);
        this.addEntry(entry);
    }
}
