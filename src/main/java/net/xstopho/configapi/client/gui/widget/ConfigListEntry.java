package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.xstopho.configapi.ConfigApi;

import java.util.List;

public class ConfigListEntry extends ContainerObjectSelectionList.Entry<ConfigListEntry> {

    private final int index;
    private final ItemLike item;
    private final Minecraft client;

    public ConfigListEntry(int index, ItemLike item) {
        this.index = index;
        this.item = item;

        client = Minecraft.getInstance();
        int xPos = (client.getWindow().getGuiScaledWidth() / 10) + 1;
        this.setX(xPos);
        if (ConfigApi.bufferInit != xPos) {
            ConfigApi.bufferInit = xPos;
            ConfigApi.LOGGER.error("Init xPos = {}", xPos);
        }
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return List.of();
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float f) {
        if (ConfigApi.bufferPos != this.getContentX()) {
            ConfigApi.bufferPos = this.getContentX();
            ConfigApi.LOGGER.error("xPos = {}, yPos = {}, screenWidth = {}", this.getContentX(), this.getContentY(), this.getWidth());

            int screenWidth = client.getWindow().getGuiScaledWidth();
            int xPos = (screenWidth / 10) + 3;
            ConfigApi.LOGGER.error("approximated xPos = {}", xPos);
        }
        guiGraphics.renderItem(new ItemStack(this.item, 1), this.getContentX(), this.getContentY());
        guiGraphics.drawString(Minecraft.getInstance().gui.getFont(), Component.literal("Index: " + this.index), this.getContentX() + 32, this.getContentY() + 5, -1, false);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
