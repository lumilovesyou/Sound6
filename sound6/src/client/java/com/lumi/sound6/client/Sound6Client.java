package com.lumi.sound6.client;

import com.lumi.sound6.client.gui.SoundBoardGUI;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import static com.lumi.sound6.Sound6.MOD_ID;

public class Sound6Client implements ClientModInitializer {
	//Register keybinds
	KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(MOD_ID, "keymaps_category")
	);
	KeyMapping openGuiKey = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					String.format("key.%s.open_gui", MOD_ID),
					InputConstants.Type.KEYSYM,
					InputConstants.KEY_V,
					this.KEY_CATEGORY
			)
	);

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (this.openGuiKey.consumeClick()) {
				if (client.player == null) return;

				Minecraft.getInstance().gui.setScreen(
						new SoundBoardGUI(Component.empty())
				);
			}
		});
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}