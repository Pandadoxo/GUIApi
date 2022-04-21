///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 12:02     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.result;

import de.pandadoxo.guiapi.interfaces.PressListener;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;

public class GuiButton {

    // item
    private final GuiItem guiItem;
    private final int slot;

    // press
    private final PressListener pressListener;
    private final Sound pressSound;
    private final int volume;
    private final int pitch;

    public GuiButton(GuiItem guiItem, int slot, PressListener pressListener, Sound pressSound, int volume, int pitch) {
        this.guiItem = guiItem;
        this.slot = slot;
        this.pressListener = pressListener;
        this.pressSound = pressSound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public GuiItem getGuiItem() {
        return guiItem;
    }

    public int getSlot() {
        return slot;
    }

    public PressListener getPressListener() {
        return pressListener;
    }

    public Sound getPressSound() {
        return pressSound;
    }

    public int getVolume() {
        return volume;
    }

    public int getPitch() {
        return pitch;
    }
}
