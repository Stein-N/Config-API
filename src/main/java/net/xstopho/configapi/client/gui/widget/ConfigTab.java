package net.xstopho.configapi.client.gui.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;

public class ConfigTab implements Tab {

    private final String title;
    private final ConfigListWidget configList;

    public ConfigTab(String title, List<ConfigListEntry> entries) {
        this.title = title;

        this.configList = new ConfigListWidget(0, 0, 23, 24);
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
        this.configList.setRectangle(screenRectangle.width(), screenRectangle.height(), 0,23);
    }
}
