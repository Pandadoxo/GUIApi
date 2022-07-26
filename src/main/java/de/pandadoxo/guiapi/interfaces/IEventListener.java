///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:41     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.interfaces;

import de.pandadoxo.guiapi.result.GuiMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;

public class IEventListener {

    public static interface OnEventListener {
        void onEvent(InventoryEvent event, GuiMenu menu);
    }

    public static OnEventListener DefaultOnEventListener() {
        return (event, menu) -> {
        };
    }

    public interface OnClickListener {
        void onClick(InventoryClickEvent event, GuiMenu menu);
    }

    public static OnClickListener DefaultOnClickListener() {
        return (event, menu) -> {
            event.setCancelled(true);
        };
    }

    public interface OnDragListener {
        void onDrag(InventoryDragEvent event, GuiMenu menu);
    }

    public static OnDragListener DefaultOnDragListener() {
        return (event, menu) -> {
            event.setCancelled(true);
        };
    }

    public interface OnCloseListener {
        void onClose(InventoryCloseEvent event, GuiMenu menu);
    }

    public static OnCloseListener DefaultOnCloseListener() {
        return (event, menu) -> {
            if (!menu.isReOpen()) {
                menu.destroy();
            }
        };
    }

}
