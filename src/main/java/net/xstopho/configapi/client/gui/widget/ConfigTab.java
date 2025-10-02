package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;

public class ConfigTab implements Tab {

    private final String title;
    private final HeaderAndFooterLayout layout;
    private final ConfigListWidget configList;

    public ConfigTab(String title, List<ConfigListEntry> entries, HeaderAndFooterLayout layout) {
        this.title = title;
        this.layout = layout;

        this.configList = new ConfigListWidget(layout.getWidth(), layout.getContentHeight(), layout.getHeaderHeight(), 24);
        this.configList.replaceEntries(entries);
    }

    @Override
    public Component getTabTitle() {
        return Component.literal(title);
    }

    @Override
    public Component getTabExtraNarration() {
        return Component.literal(title);
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        consumer.accept(this.configList);
    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {
        this.configList.setRectangle(screenRectangle.width(), screenRectangle.height(), 0, layout.getHeaderHeight());
    }
}
