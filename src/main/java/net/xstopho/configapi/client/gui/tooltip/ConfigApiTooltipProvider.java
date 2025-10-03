package net.xstopho.configapi.client.gui.tooltip;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

import java.util.List;

public interface ConfigApiTooltipProvider {
    List<ClientTooltipComponent> getTooltip();
}
