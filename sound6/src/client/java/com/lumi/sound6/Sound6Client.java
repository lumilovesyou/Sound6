package com.lumi.sound6;

import com.lumi.sound6.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import com.lumi.sound6.gui.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sound6Client implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("sound6");
	public static Config CONFIG;

	@Override
	public void onInitializeClient() {
		CONFIG = Config.load();
		CONFIG.save();
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		KeyBinding openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.sound6.keybinds.menu",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_V,
				"category.sound6.keybinds.category"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openMenu.wasPressed()) {
				client.player.sendMessage(Text.literal("Meow :3"), false);
				MinecraftClient.getInstance().setScreen(new Menu());
			}
		});
	}
}