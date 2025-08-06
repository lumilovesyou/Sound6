package com.lumi.sound6;

import com.lumi.sound6.network.SoundPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sound6 implements ModInitializer {
	public static final String MOD_ID = "sound6";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static RegistryEntry<SoundEvent> getSoundEventFromId(String soundId) {
		try {
			net.minecraft.util.Identifier identifier = Identifier.of(soundId);
			return Registries.SOUND_EVENT.getEntry(identifier).orElse(null);
		} catch (Exception e) {
            LOGGER.error("Invalid sound ID format: {}", soundId);
			return null;
		}
	}

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(SoundPacket.PlaySoundPayload.ID, SoundPacket.PlaySoundPayload.CODEC);
		LOGGER.info("{} says hello! :3", MOD_ID);
		ServerPlayNetworking.registerGlobalReceiver(SoundPacket.PlaySoundPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				RegistryEntry<SoundEvent> soundEvent = getSoundEventFromId(payload.soundID());
				if (soundEvent != null) {
					context.player().getWorld().playSound(
							null,
							context.player().getX(),
							context.player().getY(),
							context.player().getZ(),
							soundEvent,
							SoundCategory.PLAYERS,
							payload.volume(),
							payload.pitch()
					);
				} else {
					LOGGER.warn("Could not find sound event for ID: " + payload.soundID());
				}
			});
		});
	}
}