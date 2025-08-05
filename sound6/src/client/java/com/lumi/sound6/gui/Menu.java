package com.lumi.sound6.gui;

import com.lumi.sound6.config.Config;
import com.lumi.sound6.network.SoundPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.LoggerFactory;

public class Menu extends Screen {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("sound6");
    public static Config CONFIG;
    private TextFieldWidget textField;
    private ItemIconButton[] soundButtons = new ItemIconButton[6];

    public Menu() {
        super(Text.literal("Custom Gui"));
    }

    @Override
    protected void init() {
        CONFIG = Config.load();
        //Clean this garbage up later
        for (int i = 0; i < 6; i++) {
            Config.SoundButtonValues value = CONFIG.soundButtons.get(i + 1);
            if (value != null) {
                ItemStack translatedItem = new ItemStack(Registries.ITEM.get(Identifier.ofVanilla(value.itemID)));
                ItemIconButton itemButton = new ItemIconButton(
                        width / 2 - (CONFIG.buttonSize / 2) + (int) value.xOffset, height / 2 - (CONFIG.buttonSize / 2) + (int) value.yOffset,
                        CONFIG.buttonSize, CONFIG.buttonSize,
                        translatedItem,
                        button -> {
                            float pitch = 1.0f;
                            SoundPacket.PlaySoundPayload payload = new SoundPacket.PlaySoundPayload(value.soundID, CONFIG.volume, pitch);
                            ClientPlayNetworking.send(payload);
                            if (CONFIG.closeMenuOnClick) {
                                this.close();
                            }
                            if (CONFIG.sendMessages) {
                                MinecraftClient.getInstance().player.sendMessage(Text.literal(String.format("Played sound %s.", String.valueOf(value.soundID))), !CONFIG.sendInChat);
                            }
                        }
                );
                soundButtons[i] = itemButton;
                soundButtons[i].setTooltip(Tooltip.of(Text.literal(String.valueOf(value.soundID))));
                addDrawableChild(soundButtons[i]);
            }
        }
        for (int i = 0; i < 3; i++) {
            Config.VolumeButtonValues value = CONFIG.volumeButtons.get(i + 1);
            if (value != null) {
                ItemStack translatedItem = new ItemStack(Registries.ITEM.get(Identifier.ofVanilla(value.itemID)));
                ItemIconButton itemButton = new ItemIconButton(
                        width / 2 - (CONFIG.buttonSize / 2) + (int) value.xOffset, height / 2 - (CONFIG.buttonSize / 2) + (int) value.yOffset,
                        CONFIG.buttonSize, CONFIG.buttonSize,
                        translatedItem,
                        button -> {
                            setVolume(value.volume);
                            if (CONFIG.closeMenuOnClick) {
                                this.close();
                            }
                        }
                );
                soundButtons[i] = itemButton;
                soundButtons[i].setTooltip(Tooltip.of(Text.literal("Volume: " + String.valueOf(value.volume))));
                addDrawableChild(soundButtons[i]);
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(client.textRenderer, "Click a Button", width / 2, height / 2, 0xFFFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode >= GLFW.GLFW_KEY_1 && keyCode <= GLFW.GLFW_KEY_6) {
            int buttonIndex = keyCode - GLFW.GLFW_KEY_1;
            soundButtons[buttonIndex].onPress();
            return true;
        } else if (keyCode >= GLFW.GLFW_KEY_7 && keyCode <= GLFW.GLFW_KEY_9) {
            setVolume(((keyCode - GLFW.GLFW_KEY_7) / 2) + 0.5f);
            return true;
        }
        switch (keyCode) {
            case GLFW.GLFW_KEY_R:
                CONFIG = Config.load();
                MinecraftClient.getInstance().player.sendMessage(Text.literal("Reloaded!"), false);

            case GLFW.GLFW_KEY_C:
                this.close();
                return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void setVolume(float volume) {
        CONFIG.volume = volume;
        CONFIG.save();
        if (CONFIG.sendMessages) {
            MinecraftClient.getInstance().player.sendMessage(Text.literal(String.format("Volume changed to %f.", volume)), !CONFIG.sendInChat);
        }
    }
}
