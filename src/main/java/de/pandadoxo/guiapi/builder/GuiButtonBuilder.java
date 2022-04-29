///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 12:02     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.builder;

import de.pandadoxo.guiapi.interfaces.PressListener;
import de.pandadoxo.guiapi.result.GuiButton;
import de.pandadoxo.guiapi.result.GuiItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;

public class GuiButtonBuilder implements Cloneable{

    // item
    private GuiItem guiItem;
    private int slot;

    // press
    private PressListener pressListener;
    private Sound pressSound;
    private int volume;
    private int pitch;

    public GuiButtonBuilder() {
    }

    public GuiButton create() {
        Validate.isTrue(guiItem != null, "GuiItem needs to be specified!");

        return new GuiButton(guiItem, slot, pressListener, pressSound, volume, pitch);
    }

    public GuiButtonBuilder setGuiItem(GuiItem guiItem) {
        this.guiItem = guiItem;
        return this;
    }

    public GuiButtonBuilder setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public GuiButtonBuilder setPressListener(PressListener pressListener) {
        this.pressListener = pressListener;
        return this;
    }

    public GuiButtonBuilder setPressSound(Sound pressSound) {
        this.pressSound = pressSound;
        return this;
    }

    public GuiButtonBuilder setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public GuiButtonBuilder setPitch(int pitch) {
        this.pitch = pitch;
        return this;
    }

    @Override
    public GuiButtonBuilder clone() {
        try {
            GuiButtonBuilder clone = (GuiButtonBuilder) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
