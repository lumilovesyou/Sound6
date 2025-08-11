package com.lumi.sound6.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.io.*;

import static com.lumi.sound6.Sound6.MOD_ID;

public class Config {
    public interface ButtonValues {
        String getItemId();
        Integer getXOffset();
        Integer getYOffset();
    }

    public static class SoundButtonValues implements ButtonValues {
        public String itemID;
        public String soundID;
        public Integer xOffset;
        public Integer yOffset;

        public SoundButtonValues(String a, String b, Integer c, Integer d) {
            this.itemID = a;
            this.soundID = b;
            this.xOffset = c;
            this.yOffset = d;
        }

        @Override
        public String getItemId() { return itemID; }
        @Override
        public Integer getXOffset() { return xOffset; }
        @Override
        public Integer getYOffset() { return yOffset; }

        public String getSoundID() { return soundID; }
    }

    public static class VolumeButtonValues implements ButtonValues {
        public String itemID;
        public Float volume;
        public Integer xOffset;
        public Integer yOffset;

        public VolumeButtonValues(String a, Float b, Integer c, Integer d) {
            this.itemID = a;
            this.volume = b;
            this.xOffset = c;
            this.yOffset = d;
        }

        @Override
        public String getItemId() { return itemID; }
        @Override
        public Integer getXOffset() { return xOffset; }
        @Override
        public Integer getYOffset() { return yOffset; }

        public Float getVolume() { return volume; }
    }

    public HashMap<Integer, SoundButtonValues> soundButtons = new HashMap<>() {{
        put(1, new SoundButtonValues("cat_spawn_egg", "entity.cat.ambient", 0, -100));
        put(2, new SoundButtonValues("air", "", 80, -40));
        put(3, new SoundButtonValues("air", "", 80, 40));
        put(4, new SoundButtonValues("air", "", 0, 80));
        put(5, new SoundButtonValues("air", "", -80, 40));
        put(6, new SoundButtonValues("air", "", -80, -40));
    }};
    public HashMap<Integer, VolumeButtonValues> volumeButtons = new HashMap<>() {{
        put(1, new VolumeButtonValues("gunpowder", 0.5f, -40, 110));
        put(2, new VolumeButtonValues("redstone", 1.0f, 0, 110));
        put(3, new VolumeButtonValues("glowstone_dust", 1.5f, 40, 110));
    }};
    public Float volume = 1.0f;
    public Integer buttonSize = 20;
    public Boolean closeMenuOnClickSound = true;
    public Boolean closeMenuOnClickVolume = false;
    public Boolean sendMessages = true;
    public Boolean sendInChat = false;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File configFile = new File(String.format("config/%s.json", MOD_ID));

    public static Config load() {
        if (!configFile.exists()) {
            Config defaultConfig = new Config();
            defaultConfig.save();
            return defaultConfig;
        }
        try (Reader reader = new FileReader(configFile)) {
            return GSON.fromJson(reader, Config.class);
        } catch (IOException error) {
            error.printStackTrace();
            return new Config();
        }
    }

    public void save() {
        try (Writer writer = new FileWriter(configFile)) {
            GSON.toJson(this, writer);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
