///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:28     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.menu;

import de.pandadoxo.guiapi.interfaces.AGuiMenu;
import de.pandadoxo.guiapi.result.GuiMenu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class GuiFurnace extends AGuiMenu {

    public GuiFurnace(GuiMenu parent) {
        super(parent);
    }

    @Override
    public Inventory create() {
        return Bukkit.createInventory(null, InventoryType.FURNACE, parent.getTitle());
    }
}
