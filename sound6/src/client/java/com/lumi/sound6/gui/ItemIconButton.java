package com.lumi.sound6.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ItemIconButton extends ButtonWidget {
    private final ItemStack iconItem;

    public ItemIconButton(int x, int y, int width, int height, ItemStack icon, PressAction onPress) {
        super(x, y, width, height, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER);
        this.iconItem = icon;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);

        int iconX = getX() + (getWidth() - 16) / 2;
        int iconY = getY() + (getHeight() - 16) / 2;
        context.drawItem(iconItem, iconX, iconY);

        if (iconItem.getCount() > 1) {
            context.drawStackOverlay(MinecraftClient.getInstance().textRenderer, iconItem, iconX, iconY);
        }
    }
}