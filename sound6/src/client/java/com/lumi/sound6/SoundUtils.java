package com.lumi.sound6;

import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.sound.SoundCategory;
import net.minecraft.entity.player.PlayerEntity;

public class SoundUtils {

    /**
     * Converts a sound ID string to a SoundEvent
     * @param soundId The sound ID string (e.g., "minecraft:entity.cat.ambient")
     * @return SoundEvent or null if not found
     */
    public static SoundEvent getSoundEventFromId(String soundId) {
        try {
            Identifier identifier = Identifier.of(soundId);
            return Registries.SOUND_EVENT.get(identifier);
        } catch (Exception e) {
            System.err.println("Invalid sound ID format: " + soundId);
            return null;
        }
    }


    /**
     * Complete example of playing a sound from a string ID
     * @param world The world to play the sound in
     * @param pos The position to play the sound at
     * @param soundId The sound ID string
     * @param category The sound category
     * @param volume Volume (1.0f is normal)
     * @param pitch Pitch (1.0f is normal)
     */
    public static void playSoundFromId(World world, BlockPos pos, String soundId,
                                       SoundCategory category, float volume, float pitch) {
        SoundEvent soundEvent = getSoundEventFromId(soundId);

        if (soundEvent != null) {
            // Server-side sound playing (heard by all players)
            world.playSound(
                    null,           // Player (null means all players hear it)
                    pos,            // Position
                    soundEvent,     // The sound event
                    category,       // Sound category
                    volume,         // Volume
                    pitch           // Pitch
            );
        } else {
            System.err.println("Could not play sound: " + soundId);
        }
    }

    /**
     * Play sound for a specific player only
     * @param world The world
     * @param player The player to play the sound for
     * @param pos The position
     * @param soundId The sound ID string
     * @param category Sound category
     * @param volume Volume
     * @param pitch Pitch
     */
    public static void playSoundForPlayer(World world, PlayerEntity player, BlockPos pos,
                                          String soundId, SoundCategory category,
                                          float volume, float pitch) {
        SoundEvent soundEvent = getSoundEventFromId(soundId);

        if (soundEvent != null) {
            // Client-side sound playing (only this player hears it)
            world.playSound(
                    player,         // Only this player hears it
                    pos,            // Position
                    soundEvent,     // The sound event
                    category,       // Sound category
                    volume,         // Volume
                    pitch           // Pitch
            );
        }
    }

    /**
     * Example usage method
     */
    public static void exampleUsage(World world, BlockPos pos) {
        // Example 1: Basic conversion and playing
        String soundId = "minecraft:entity.cat.ambient";
        SoundEvent catSound = getSoundEventFromId(soundId);

        if (catSound != null) {
            world.playSound(null, pos, catSound, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        }

        // Example 2: Using the helper method
        playSoundFromId(world, pos, "minecraft:block.note_block.harp",
                SoundCategory.BLOCKS, 0.5f, 1.2f);

        // Example 3: Custom datapack sound
        playSoundFromId(world, pos, "exampledatapack:entity.tree.walk",
                SoundCategory.NEUTRAL, 1.0f, 0.8f);
    }
}