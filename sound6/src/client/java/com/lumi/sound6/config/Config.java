package com.lumi.sound6.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.io.*;

public class Config {
    public class ConfigValues {
        public String itemID;
        public String soundID;
        public Integer xOffset;
        public Integer yOffset;

        public ConfigValues(String a, String b, Integer c, Integer d) {
            this.itemID = a;
            this.soundID = b;
            this.xOffset = c;
            this.yOffset = d;
        }
    }

    public HashMap<Integer, ConfigValues> menu = new HashMap<>() {{
        put(1, new ConfigValues("cat_spawn_egg", "entity.cat.ambient", 0, -100));
        put(2, new ConfigValues("air", "", 100, -50));
        put(3, new ConfigValues("air", "", 100, 50));
        put(4, new ConfigValues("air", "", 0, 100));
        put(5, new ConfigValues("air", "", -100, 50));
        put(6, new ConfigValues("air", "", -100, -50));
    }};
    public Float volume = 1.0f;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File configFile = new File("config/sound6.json");

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
