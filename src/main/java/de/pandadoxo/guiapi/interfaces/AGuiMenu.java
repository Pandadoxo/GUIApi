///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:31     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.interfaces;

import de.pandadoxo.guiapi.result.GuiMenu;
import org.bukkit.inventory.Inventory;

public abstract class AGuiMenu {

    protected final GuiMenu parent;
    protected Inventory inventory;

    public AGuiMenu(GuiMenu parent) {
        this.parent = parent;
    }

    public abstract Inventory create();

    public Inventory getInventory() {
        if (inventory == null) {
            inventory = create();
        }
        return inventory;
    }

    public AGuiMenu setInventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public GuiMenu getParent() {
        return parent;
    }
}
