///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:05     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.interfaces;

import de.pandadoxo.guiapi.result.GuiButton;
import de.pandadoxo.guiapi.result.GuiItem;
import de.pandadoxo.guiapi.result.GuiMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface PressListener {

    void performAction(ClickType clickType, GuiMenu guiMenu, GuiButton button, Player player);


}
