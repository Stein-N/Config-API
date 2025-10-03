package net.xstopho.configapi.client.gui.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public record ConfigApiTooltip(List<FormattedCharSequence> sequence) implements ClientTooltipComponent {

    @Override
    public int getHeight(Font font) {
        return font.lineHeight * sequence.size();
    }

    @Override
    public int getWidth(Font font) {
        return 100;
    }

    @Override
    public void renderText(GuiGraphics guiGraphics, Font font, int mouseX, int mouseY) {
        for (int i = 0; i < this.sequence.size(); i++) {
            guiGraphics.drawString(font, sequence.get(i), mouseX,
                    mouseY + (font.lineHeight * i), -1, true);
        }
    }
}
