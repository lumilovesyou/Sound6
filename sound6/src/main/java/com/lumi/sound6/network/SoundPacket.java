package com.lumi.sound6.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SoundPacket {
    public static final Identifier SOUND_PACKET_ID = Identifier.of("sound6", "play_sound");

    public record PlaySoundPayload(String soundID, float volume, float pitch) implements CustomPayload {
        public static final CustomPayload.Id<PlaySoundPayload> ID = new CustomPayload.Id<>(SOUND_PACKET_ID);

        public static final PacketCodec<RegistryByteBuf, PlaySoundPayload> CODEC = PacketCodec.tuple(
                PacketCodecs.STRING, PlaySoundPayload::soundID,
                PacketCodecs.FLOAT, PlaySoundPayload::volume,
                PacketCodecs.FLOAT, PlaySoundPayload::pitch,
                PlaySoundPayload::new
        );

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}