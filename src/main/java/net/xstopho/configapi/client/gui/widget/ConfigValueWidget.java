package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.xstopho.configapi.annotations.ConfigEntry;
import net.xstopho.configapi.client.gui.tooltip.ConfigApiTooltipProvider;
import net.xstopho.configapi.client.gui.utils.GuiUtils;

import java.lang.reflect.Field;
import java.util.List;

//TODO: - add change state management
//      - add save and reset methods
//      - add generic getter and setter for values
//      - add T Type for the class
public class ConfigValueWidget extends AbstractWidget implements ConfigApiTooltipProvider {
    private final ConfigEntry entry;
    private final Field field;

    public ConfigValueWidget(int width, int height, Field field, ConfigEntry entry) {
        super(0, 0, width, height, CommonComponents.EMPTY);
        this.entry = entry;
        this.field = field;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if (this.isHovered()) {
            guiGraphics.fill(this.getX(), this.getY(), this.getRight(), this.getBottom(), 0x18FFFFFF);
        }

        guiGraphics.drawString(GuiUtils.getFont(), Component.literal(this.field.getName()), this.getX(), this.getY(), -1);
    }

    @Override
    protected final void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

    @Override
    public List<ClientTooltipComponent> getTooltip() {
        return List.of();
    }
}
