package net.xstopho.configapi.client.gui.widgets.config_list;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.xstopho.configapi.client.gui.utils.GuiUtils;
import net.xstopho.configapi.config.ModConfig;

public class ConfigListEntry extends ObjectSelectionList.Entry<ConfigListEntry> {

    private final Component configName;

    public ConfigListEntry(ModConfig modConfig) {
        this.configName = Component.literal(modConfig.getConfigName());
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        return super.mouseClicked(mouseButtonEvent, bl);
    }

    @Override
    public Component getNarration() {
        return configName;
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float delta) {
        guiGraphics.drawString(GuiUtils.getFont(), this.configName, this.getContentX(), this.getContentY(), -1, false);
    }
}
