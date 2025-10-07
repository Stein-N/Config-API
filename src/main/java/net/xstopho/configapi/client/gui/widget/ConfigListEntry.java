package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.xstopho.configapi.annotations.ConfigEntry;
import net.xstopho.configapi.client.gui.utils.GuiUtils;
import net.xstopho.configapi.config.ModConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigListEntry extends ContainerObjectSelectionList.Entry<ConfigListEntry> {

    private final List<ConfigValueWidget> values = new ArrayList<>();
    private final ModConfig config;

    public ConfigListEntry(ModConfig config) {
        this.config = config;

        config.getValues().forEach(this::addValue);
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.values;
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean isHovering, float partialTick) {
        // Render Highlight Background
        if (mouseY >= this.getContentY() && mouseY <= this.getContentY() + 24) {
            guiGraphics.fill(this.getX(), this.getY(), this.getContentRight(), this.getY() + 24, 0x18FFFFFF);
        }

        // Render Config Filename
        Component title = Component.literal(this.config.getConfigName());
        int xPos = (GuiUtils.getScaledGuiWidth() / 2) - (GuiUtils.getFont().width(title.getString()) / 2);
        guiGraphics.drawString(GuiUtils.getFont(), title, xPos, this.getContentY(), 0xff4ef542);

        // Render Config Values when extended
        if (this.isExtended()) {
            for (int index = 0; index < this.values.size(); index++) {
                ConfigValueWidget widget = this.values.get(index);
                widget.setPosition(this.getContentX(), this.getContentY() + 24 + (24 * index));
                widget.render(guiGraphics, mouseX, mouseY, partialTick);
            }
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        if (event.y() > this.getY() && event.y() < this.getY() + 24) {
            this.changeHeight();
        }
        return super.mouseClicked(event, isDoubleClick);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return this.values;
    }

    public void addValue(Field field, ConfigEntry entry) {
        ConfigValueWidget widget = new ConfigValueWidget(this.getWidth(), this.getHeight(), field, entry);
        if (!this.values.contains(widget)) {
            this.values.add(widget);
        }
    }

    private boolean isExtended() {
        return this.getHeight() != 24;
    }

    private void changeHeight() {
        int height;
        if (isExtended()) {
            height = 24;
        } else {
            height = (this.getHeight() * this.values.size()) + this.getHeight();
        }
        this.setHeight(height);
    }
}
