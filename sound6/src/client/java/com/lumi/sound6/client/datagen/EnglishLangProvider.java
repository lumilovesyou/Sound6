package com.lumi.sound6.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static com.lumi.sound6.Sound6.MOD_ID;

public class EnglishLangProvider extends FabricLanguageProvider {
    public EnglishLangProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(String.format("key.category.%s.keymaps_category", MOD_ID), "Sound6");
        translationBuilder.add(String.format("key.%s.open_gui", MOD_ID), "Open GUI");
    }
}
