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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lumi.sound6.Sound6.MOD_ID;

public class Menu extends Screen {
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Config CONFIG;
    private TextFieldWidget textField;
    private ItemIconButton[] soundButtons = new ItemIconButton[6];

    public Menu() {
        super(Text.literal("Custom Gui"));
    }

    private <T extends Config.ButtonValues>ItemIconButton makeButton(T value) {
        ItemStack translatedItem = new ItemStack(Registries.ITEM.get(Identifier.of(value.getItemId())));
        ItemIconButton itemButton = new ItemIconButton(
                width / 2 - (CONFIG.buttonSize / 2) + value.getXOffset(), height / 2 - (CONFIG.buttonSize / 2) + value.getYOffset(),
                CONFIG.buttonSize, CONFIG.buttonSize,
                translatedItem,
                button -> {
                    String messageText = "";
                    if (value instanceof Config.SoundButtonValues) {
                        float pitch = 1.0f; //Do I just hardcode this value below or should I make a way to edit this in the config? I don't see why people would want that though
                        SoundPacket.PlaySoundPayload payload = new SoundPacket.PlaySoundPayload(((Config.SoundButtonValues) value).getSoundID(), CONFIG.volume, pitch);
                        ClientPlayNetworking.send(payload);
                        if (CONFIG.closeMenuOnClick) {
                            this.close();
                        }
                        messageText = String.format("Played sound %s.", ((Config.SoundButtonValues) value).getSoundID());
                    } else if (value instanceof Config.VolumeButtonValues){
                        messageText = "Volume: " + ((Config.VolumeButtonValues) value).getVolume();
                        setVolume(((Config.VolumeButtonValues) value).getVolume());
                        if (CONFIG.closeMenuOnClick) {
                            this.close();
                        }
                    }
                    if (CONFIG.sendMessages) {
                        MinecraftClient.getInstance().player.sendMessage(Text.literal(messageText), !CONFIG.sendInChat);
                    }
                }
        );
        return itemButton;
    }

    @Override
    protected void init() {
        CONFIG = Config.load();
        for (int i = 0; i < 6; i++) {
            Config.SoundButtonValues value = CONFIG.soundButtons.get(i + 1);
            if (value != null) {
                soundButtons[i] = makeButton(value);
                soundButtons[i].setTooltip(Tooltip.of(Text.literal(String.valueOf(value.soundID))));
                addDrawableChild(soundButtons[i]);
            }
        }
        for (int i = 0; i < 3; i++) {
            Config.VolumeButtonValues value = CONFIG.volumeButtons.get(i + 1);
            if (value != null) {
                soundButtons[i] = makeButton(value);
                soundButtons[i].setTooltip(Tooltip.of(Text.literal("Volume: " + value.volume)));
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
