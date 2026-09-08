package com.lumi.sound6;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.lumi.sound6.Sound6.LOGGER;
import static com.lumi.sound6.Sound6.MOD_ID;

public class Config {
    public boolean modEnabled = true;
    public ButtonValues testButton = new ButtonValues("minecraft:diamond", "soundID", 50, 50);
    public boolean modDisEnabled = true;

    public static class ButtonValues {
        public String itemID;
        public String soundID;
        public Integer percentageXOffset;
        public Integer percentageYOffset;

        public ButtonValues(String a, String b, Integer c, Integer d) {
            this.itemID = a;
            this.soundID = b;
            this.percentageXOffset = c;
            this.percentageYOffset = d;
        }
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve(String.format("%s.json", MOD_ID));

    public static Config load() {
        if (Files.exists(PATH)) {
            try (Reader reader = Files.newBufferedReader(PATH)) {
                return GSON.fromJson(reader, Config.class);
            } catch (IOException e) {
                LOGGER.error("Failed to load Sound6 config: {e}");
            }
        }
        Config config = new Config();
        config.save();
        return config;
    }

    public void save() {
        try (Writer writer = Files.newBufferedWriter(PATH)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save Sound6 config: {e}");
        }
    }
}
