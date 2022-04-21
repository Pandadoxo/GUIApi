///////////////////////////////
// Class coded by Pandadoxo  //
// on 25.02.2022 at 08:06     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.menu;

import de.pandadoxo.guiapi.interfaces.AGuiMenu;
import de.pandadoxo.guiapi.result.GuiMenu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class GuiSmithing extends AGuiMenu {

    public GuiSmithing(GuiMenu parent) {
        super(parent);
    }

    @Override
    public Inventory create() {
        return Bukkit.createInventory(null, InventoryType.SMITHING, parent.getTitle());
    }
}
