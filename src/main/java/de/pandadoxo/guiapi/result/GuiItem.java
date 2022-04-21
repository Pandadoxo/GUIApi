///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:07     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.result;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiItem implements Cloneable {

    private final Material type;
    private final String name;
    private final String[] lore;
    private final ItemStack itemStack;

    public GuiItem(Material type, String name, String[] lore, ItemStack itemStack) {
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.itemStack = itemStack;
    }

    public Material getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String[] getLore() {
        return lore;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
