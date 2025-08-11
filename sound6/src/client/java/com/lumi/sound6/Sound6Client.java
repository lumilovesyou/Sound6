package com.lumi.sound6;

import com.lumi.sound6.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.lumi.sound6.gui.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lumi.sound6.Sound6.MOD_ID;

public class Sound6Client implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Config CONFIG;

	@Override
	public void onInitializeClient() {
		CONFIG = Config.load();
		CONFIG.save();
		KeyBinding openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				String.format("key.%s.keybinds.menu", MOD_ID),
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_V,
				String.format("category.%s.keybinds.category", MOD_ID)
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openMenu.wasPressed()) {
				MinecraftClient.getInstance().setScreen(new Menu());
			}
		});
	}
}