package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.xstopho.configapi.client.gui.screen.ConfigApiScreen;
import net.xstopho.configapi.client.gui.tooltip.ConfigApiTooltip;
import net.xstopho.configapi.client.gui.tooltip.ConfigApiTooltipProvider;
import net.xstopho.configapi.client.gui.utils.GuiUtils;

import java.util.List;

public class ConfigListEntry extends ContainerObjectSelectionList.Entry<ConfigListEntry> implements ConfigApiTooltipProvider {

    private final int index;
    private final ItemLike item;
    private final ConfigApiScreen screen;

    public ConfigListEntry(int index, ItemLike item, ConfigApiScreen screen) {
        this.index = index;
        this.item = item;
        this.screen = screen;
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return List.of();
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float f) {
        this.setX((GuiUtils.getScaledGuiWidth() / 10) + 1);
        guiGraphics.renderItem(new ItemStack(this.item, 1), this.getContentX(), this.getContentY());
        guiGraphics.drawString(Minecraft.getInstance().gui.getFont(), Component.literal("Index: " + this.index).withColor(0x4ef542), this.getContentX() + 32, this.getContentY() + 5, -1, false);

        if (this.getContentX() <= mouseX && (this.getContentX() + 16) >= mouseX &&
            this.getContentY() <= mouseY && (this.getContentY() + 16) >= mouseY) {
            this.screen.addTooltip(this);
        } else {
            this.screen.removeTooltip(this);
        }
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    private List<FormattedCharSequence> dummy() {
        Component tooltip = Component.literal("Index: " + index + "\n" + "This is a random Text to Test if i can fix that dumb overlapping issue that i have since 1.21.4");
        return GuiUtils.getFont().split(tooltip, 100);
    }

    @Override
    public List<ClientTooltipComponent> getTooltip() {
        return List.of(new ConfigApiTooltip(dummy()));
    }
}
