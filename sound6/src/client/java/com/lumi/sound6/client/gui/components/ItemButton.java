package com.lumi.sound6.client.gui.components;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ItemButton extends Button {
    protected final ItemStack itemStack;

    public ItemButton(final int x, final int y, final int width, final int height, final ItemStack itemStack, final OnPress onPress) {
        this(x, y, width, height, itemStack, onPress, Component.empty());
    }

    public ItemButton(int x, int y, int width, int height, ItemStack itemStack, OnPress onPress, Component message) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.itemStack = itemStack;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        this.extractDefaultSprite(graphics);
        if (!this.itemStack.isEmpty()) {
            int itemX = this.getX() + (this.getWidth() - 16) / 2;
            int itemY = this.getY() + (this.getHeight() - 16) / 2;
            graphics.fakeItem(this.itemStack, itemX, itemY);
        }
    }
}