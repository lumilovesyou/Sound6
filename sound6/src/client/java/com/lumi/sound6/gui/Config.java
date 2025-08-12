package com.lumi.sound6.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lumi.sound6.Sound6.MOD_ID;

//FabricLoader.getInstance().isModLoaded("modmenu")
public class Config extends Screen { //Maybe to change to GameOptionsScreen / class_4667
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static com.lumi.sound6.config.Config CONFIG;

    public Screen parent;
    public Config(Screen parent) {
        super(Text.literal("Sound6 Config - Mod Menu Integration"));
        this.parent = parent;
    }

    private void addOption(int Id, int Y) {
        //What's this code :sob:
        //You better clean this up
        //Why did you just copy paste so much qwq
        addText("Button " + Id, Y);

        TextFieldWidget itemId = new TextFieldWidget(textRenderer, 150, 20, Text.literal(""));
        itemId.setPosition(10, Y + 10);
        itemId.setPlaceholder(Text.literal("Item ID"));
        itemId.setText(CONFIG.soundButtons.get(Id).itemID);
        itemId.setChangedListener(text -> {
            CONFIG.soundButtons.get(Id).itemID = text;
            CONFIG.save();
        });

        TextFieldWidget soundId = new TextFieldWidget(textRenderer, 150, 20, Text.literal(""));
        soundId.setPosition(165, Y + 10);
        soundId.setPlaceholder(Text.literal("Sound ID"));
        soundId.setText(CONFIG.soundButtons.get(Id).soundID);
        soundId.setChangedListener(text -> {
            CONFIG.soundButtons.get(Id).soundID = text;
            CONFIG.save();
        });

        TextFieldWidget xOffset = new TextFieldWidget(textRenderer, 40, 20, Text.literal(""));
        xOffset.setPosition(320, Y + 10);
        xOffset.setPlaceholder(Text.literal("X Position"));
        xOffset.setText(CONFIG.soundButtons.get(Id).xOffset.toString());
        xOffset.setTextPredicate(this::isValidIntInput);
        xOffset.setChangedListener(text -> {
            if (text.isEmpty()) {
                CONFIG.soundButtons.get(Id).xOffset = 0;
            } else {
                CONFIG.soundButtons.get(Id).xOffset = Integer.valueOf(text);
            }
            CONFIG.save();
        });

        TextFieldWidget yOffset = new TextFieldWidget(textRenderer, 40, 20, Text.literal(""));
        yOffset.setPosition(365, Y + 10);
        yOffset.setPlaceholder(Text.literal("Y Position"));
        yOffset.setText(CONFIG.soundButtons.get(Id).yOffset.toString());
        yOffset.setTextPredicate(this::isValidIntInput);
        yOffset.setChangedListener(text -> {
            if (text == "") {
                CONFIG.soundButtons.get(Id).yOffset = 0;
            } else {
                CONFIG.soundButtons.get(Id).yOffset = Integer.valueOf(text);
            }
            CONFIG.save();
        });

        this.addDrawable(itemId);
        this.addSelectableChild(itemId);
        this.addDrawable(soundId);
        this.addSelectableChild(soundId);
        this.addDrawable(xOffset);
        this.addSelectableChild(xOffset);
        this.addDrawable(yOffset);
        this.addSelectableChild(yOffset);
    }

    private boolean isValidIntInput(String input) {
        if (input.isEmpty()) {
            return true;
        }
        return input.matches("^-?\\d*$") && !input.equals("-"); //ew regex
    }

    private boolean isValidFloatInput(String input) {
        if (input.isEmpty()) {
            return true;
        }
        return input.matches("^-?\\d*\\.?\\d*$") && !input.equals("-") && !input.equals("."); //still ew regex
    }


    private void addText(String text, int Y) {
        TextWidget title = new TextWidget(Text.literal(text), textRenderer);
        title.setPosition(10, Y);
        this.addDrawable(title);
    }

    private void addText(String text, int Y, boolean centre, boolean bold, boolean underline) {
        Style style = Style.EMPTY;
        if (bold) style = style.withFormatting(Formatting.BOLD);
        if (underline) style = style.withFormatting(Formatting.UNDERLINE);

        TextWidget title = new TextWidget(Text.literal(text).setStyle(style), textRenderer);

        title.setWidth((int) Math.round(title.getWidth() * 1.1));
        if (centre) {
            title.setPosition((width / 2) - (title.getWidth() / 2), Y);
        } else {
            title.setPosition(10, Y);
        }
        this.addDrawable(title);
    }

    @Override
    protected void init() {
        CONFIG = com.lumi.sound6.config.Config.load();
        addText("Sound6 Config", 10, true, false, false); //Title
        //Category for sound buttons
        addText("Sound Buttons", 30, false, true, true);
        for (int i = 1; i <= 6; i++) {
            addOption(i, 50 + ((i - 1) * 40));
        }
        //
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
