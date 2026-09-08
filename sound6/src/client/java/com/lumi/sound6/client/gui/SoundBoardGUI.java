package com.lumi.sound6.client.gui;

import com.lumi.sound6.client.gui.components.ItemButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

public class SoundBoardGUI extends Screen {
    public SoundBoardGUI(Component title) {
        super(title);
    }

    @Override
    protected void init() {

        Button buttonWidget = Button.builder(Component.literal("Hello world!"), (btn) -> {
            this.minecraft.gui.toastManager().addToast(
                    new SystemToast(SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.nullToEmpty("Hello world!"), Component.nullToEmpty("This is a toast."))
            );
        }).bounds(40, 40, 120, 20).build();


        ItemButton itemButtonWidget = new ItemButton(40, 70, 120, 20, new ItemStack(Items.DIAMOND), (btn) -> {
            this.minecraft.gui.toastManager().addToast(
                    new SystemToast(SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.nullToEmpty("Hello world!"), Component.nullToEmpty("This is a toast."))
            );
        });

        this.addRenderableWidget(buttonWidget);
        this.addRenderableWidget(itemButtonWidget);
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta); //Preserve existing rendered things...

        graphics.text(this.font, "Special button", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
    }
}
