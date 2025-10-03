package net.xstopho.configapi.client.gui.widgets;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.xstopho.configapi.api.ConfigRegistry;
import net.xstopho.configapi.client.gui.ConfigApiScreen;
import net.xstopho.configapi.config.ModConfig;

import java.util.List;
import java.util.function.Consumer;

public class ConfigTab implements Tab {

    private final ConfigRegistry.Type configType;
    private final ConfigApiScreen screen;

    public ConfigTab(ConfigRegistry.Type configType, ConfigApiScreen screen, List<ModConfig> configs, HeaderAndFooterLayout layout) {
        this.configType = configType;
        this.screen = screen;
    }

    @Override
    public Component getTabTitle() {
        return Component.literal(configType.toString());
    }

    @Override
    public Component getTabExtraNarration() {
        return Component.literal(configType.toString());
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {}

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {}
}
