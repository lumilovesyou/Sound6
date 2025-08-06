<div align="center"><center>
<img src="https://raw.githubusercontent.com/lumilovesyou/Sound6/refs/heads/main/sound6/src/main/resources/assets/sound6/icon.png">

# Sound6

*An in-game sound board.*

Sound6 adds a simple-to-use menu to play sounds from Minecraft with the intended use of roleplay.

<img src="https://cdn.modrinth.com/data/OzFxo8Bq/images/61b0714f982378306f145067d612dd3203de8079.webp" width="600vw">
</center></div>

### How to use
#### Keybinds
Pressing `v` will open the menu. From there pressing the number keys `1`-`6` will activate the equivilent button, going clockwise from the top. Pressing `7`-`9` will change volume between `0.5`-`1.5`.

#### Menu
Hovering on the six buttons in a circle will display the ID of the sound it will play. (e.g. `minecraft:entity.cat.ambient`) Clicking on one of them will play that sound and close the menu. Below the six buttons is another three. These are volume buttons. Hovering over these will display the level they'll set the volume to. (e.g. `1.0`) Clicking on one of them will update your volume to that level and close the menu.

#### Config
To customise the menu launch with the mod once and a file named `sound6.json` will appear in the config folder. From here you can edit this file and changes will be reflect in game without needing to relaunch.

Under the category `soundButtons` there are six matching blocks. Each has a number assigned and inside contain four values. The number can be ignored, though **do not** change that value. The four values inside each block customise the appearance and functionality of each button. `itemID` is the ID of the item displayed on the button, `soundID` is the ID of the sound played when the button is clicked, `xOffset` how far right from the centre of the screen the button is placed, and `yOffset` is how far down from the centre of the screen the button is placed.

```json
"1": {
      "itemID": "minecraft:cat_spawn_egg",
      "soundID": "minecraft:entity.cat.ambient",
      "xOffset": 0,
      "yOffset": -80
    }
```
Under the category `volumeButtons` there are three matching blocks. Similar to `soundButtons` each block contains four values and is assigned a number which should not be changed. The three values identical to `soundButtons` act the same as before, with the only diffence being the `volume` value. This value controls what your volume will be set to when pressed. This is a float with the range `0.0`-`2.0`.

```json
"1": {
      "itemID": "gunpowder",
      "volume": 0.5,
      "xOffset": -40,
      "yOffset": 110
    }
```

Below the two categories are a handful of other options. `volume` is your current volume and has a range of `0.0`-`2.0`, `buttonSize` controls the height and width of the menu's buttons, `closeMenuOnClick` controls whether the menu closes automatically when a button or keybind is pressed, `sendMessages` controls whether a message is sent when a button is pressed (e.g. `Volume changed to 1.5.` or `Played sound minecraft:entity.cat.ambient.`), and `sendInChat` controls whether the send message appears in chat (visible only to you) or above your hotbar, given of course `sendMessages` is set to `true`.