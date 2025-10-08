package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.xstopho.configapi.client.gui.utils.GuiUtils;
import net.xstopho.configapi.config.ModConfig;

import java.util.List;
import java.util.function.Consumer;

public class ConfigTab implements Tab {

    private final String title;
    private final HeaderAndFooterLayout layout;
    private final ConfigListWidget configList;

    public ConfigTab(String title, List<ModConfig> configs, HeaderAndFooterLayout layout) {
        this.title = title;
        this.layout = layout;

        this.configList = new ConfigListWidget(GuiUtils.getScaledGuiWidth(), layout.getContentHeight(), layout.getHeaderHeight(), configs);
    }

    @Override
    public Component getTabTitle() {
        return Component.literal(this.title);
    }

    @Override
    public Component getTabExtraNarration() {
        return this.getTabTitle();
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        consumer.accept(this.configList);
    }

    @Override
    public void doLayout(ScreenRectangle rectangle) {
        this.configList.setRectangle(rectangle.width(), rectangle.height(), 0, layout.getHeaderHeight());
    }
}
