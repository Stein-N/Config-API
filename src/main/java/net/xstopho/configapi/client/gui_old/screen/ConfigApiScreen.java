package net.xstopho.configapi.client.gui_old.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.xstopho.configapi.ConfigApi;
import net.xstopho.configapi.client.gui.tooltip.ConfigApiTooltipProvider;
import net.xstopho.configapi.client.gui_old.utils.GuiUtils;
import net.xstopho.configapi.client.gui_old.widget.ConfigListEntry;
import net.xstopho.configapi.client.gui_old.widget.ConfigTab;

import java.util.ArrayList;
import java.util.List;

public class ConfigApiScreen extends Screen {
    private final Screen parent;

    private final HeaderAndFooterLayout layout;
    private final TabManager tabManager;
    private TabNavigationBar tabNavigationBar;

    private final ConfigTab client;
    private final ConfigTab common;
    private final ConfigTab server;

    private final List<ConfigApiTooltipProvider> tooltips = new ArrayList<>();

    public ConfigApiScreen(Screen parent, String modId) {
        super(Component.literal("Config Screen - " + modId));
        this.parent = parent;

        this.layout = new HeaderAndFooterLayout(this, 24, 28);
        tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);

        client = new ConfigTab("Client", createDummyEntries(Items.DIAMOND, this), layout);
        common = new ConfigTab("Common", createDummyEntries(Items.NETHERITE_SCRAP, this), layout);
        server = new ConfigTab("Server", createDummyEntries(Items.GOLD_NUGGET, this), layout);
    }

    @Override
    protected void init() {
        TabNavigationBar.Builder builder = TabNavigationBar.builder(this.tabManager, this.width);
        builder.addTabs(client, common, server);

        this.tabNavigationBar = builder.build();

        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(Component.literal("This is a Button"), button -> ConfigApi.LOGGER.info("Button was clicked")).width(150).build());

        this.layout.visitWidgets(this::addRenderableWidget);

        this.addRenderableWidget(tabNavigationBar);
        this.tabNavigationBar.selectTab(0, true);

        this.repositionElements();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        super.render(guiGraphics, mouseX, mouseY, ticks);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Screen.FOOTER_SEPARATOR, 0, this.height - 30, 0F, 0F, this.width, 2, 30, 2);

        this.tooltips.forEach(provider -> {
            guiGraphics.renderTooltip(GuiUtils.getFont(), provider.getTooltip(), mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, null);
        });
    }

    @Override
    protected void repositionElements() {
        if (this.tabNavigationBar != null && !this.tabNavigationBar.children().isEmpty()) {
            this.tabNavigationBar.setWidth(this.width);
            this.tabNavigationBar.arrangeElements();
            int i = this.layout.getHeaderHeight();
            ScreenRectangle screenRectangle = new ScreenRectangle(0, i, this.width, this.height - (i * 2) - 6);
            this.tabManager.setTabArea(screenRectangle);
        }
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(this.parent);
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (keyEvent.key() == 256 && this.shouldCloseOnEsc()) {
            Minecraft.getInstance().setScreen(this.parent);
        }
        return super.keyPressed(keyEvent);
    }

    private List<ConfigListEntry> createDummyEntries(ItemLike item, ConfigApiScreen screen) {
        List<ConfigListEntry> entries = new ArrayList<>();
        for (int index = 1; index <= 100; index++) {
            entries.add(new ConfigListEntry(index, item, screen));
        }

        return entries;
    }

    public void addTooltip(ConfigApiTooltipProvider provider) {
        if (this.tooltips.contains(provider)) return;
        this.tooltips.add(provider);
    }

    public void removeTooltip(ConfigApiTooltipProvider provider) {
        this.tooltips.remove(provider);
    }
}
