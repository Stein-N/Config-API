package net.xstopho.configapi.client.gui.widgets.config_list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.xstopho.configapi.config.ModConfig;

import java.util.List;

public class ConfigListWidget extends ObjectSelectionList<ConfigListEntry> {

    public ConfigListWidget(int width, int height, int yPos, int defaultEntryHeight, List<ModConfig> modConfigs) {
        super(Minecraft.getInstance(), width, height, yPos, defaultEntryHeight);

        modConfigs.forEach(config -> this.addEntry(new ConfigListEntry(config)));
    }

    @Override
    public int getRowWidth() {
        return this.width - 4;
    }

    @Override
    public int getRowLeft() {
        return 7;
    }
}
