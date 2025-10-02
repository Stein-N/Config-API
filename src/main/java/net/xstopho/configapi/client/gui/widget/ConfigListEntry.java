package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class ConfigListEntry extends ContainerObjectSelectionList.Entry<ConfigListEntry> {

    private final int index;

    public ConfigListEntry(int index) {
        this.index = index;
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return List.of();
    }

    @Override
    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float f) {
        guiGraphics.renderItem(new ItemStack(Items.DIAMOND, 1), this.getContentX(), this.getContentY());
        guiGraphics.drawString(Minecraft.getInstance().gui.getFont(), Component.literal("Index: " + this.index), this.getContentX() + 32, this.getContentY() + 5, -1, false);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
