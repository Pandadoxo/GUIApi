///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:26     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.menu;

import de.pandadoxo.guiapi.interfaces.AGuiMenu;
import de.pandadoxo.guiapi.result.GuiMenu;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GuiChest extends AGuiMenu {

    private int size;

    public GuiChest(GuiMenu parent) {
        super(parent);
        this.size = 9 * 3;
    }

    @Override
    public Inventory create() {
        return Bukkit.createInventory(null, size, parent.getTitle());
    }

    public int getSize() {
        return size;
    }

    public GuiChest setSize(int size) {
        this.size = size;
        return this;
    }
}
