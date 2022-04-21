///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:07     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.result;

import de.pandadoxo.guiapi.builder.GuiButtonBuilder;
import de.pandadoxo.guiapi.builder.GuiItemBuilder;
import de.pandadoxo.guiapi.interfaces.AGuiMenu;
import de.pandadoxo.guiapi.interfaces.IEventListener;
import de.pandadoxo.guiapi.interfaces.PressListener;
import de.pandadoxo.guiapi.menu.GuiChest;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.xml.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class GuiMenu implements Listener {

    private final Plugin plugin;
    private final Player player;
    private final AGuiMenu menuType;
    private final List<GuiButton> buttons;
    private final IEventListener.OnEventListener onEventListener;
    private final IEventListener.OnClickListener onClickListener;
    private final IEventListener.OnDragListener onDragListener;
    private final IEventListener.OnCloseListener onCloseListener;

    private String title;
    private int size;
    private int curPage;
    private int maxPage;
    private Runnable onFill;

    public GuiMenu(Plugin plugin, Player player, Class<? extends AGuiMenu> clazz, String title, int size, int curPage, int maxPage, List<GuiButton> buttons, IEventListener.OnEventListener onEventListener,
                   IEventListener.OnClickListener onClickListener, IEventListener.OnDragListener onDragListener, IEventListener.OnCloseListener onCloseListener) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.plugin = plugin;
        this.player = player;
        this.menuType = clazz.getConstructor(GuiMenu.class).newInstance(this);
        this.title = title;
        this.size = size;
        this.curPage = curPage;
        this.maxPage = maxPage;
        this.buttons = buttons;
        this.onEventListener = onEventListener;
        this.onClickListener = onClickListener;
        this.onDragListener = onDragListener;
        this.onCloseListener = onCloseListener;

        // set size
        if (this.menuType instanceof GuiChest guiChest) {
            guiChest.setSize(size);
        }

        // register listener
        registerListener();
    }

    private void registerListener() {
        unregisterListener();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private void unregisterListener() {
        HandlerList.unregisterAll(this);
    }

    public void open() {
        // open
        this.player.openInventory(this.menuType.getInventory());
    }

    public void fill() {
        // fill
        if (this.onFill != null) {
            onFill.run();
        }
    }

    public void update() {
        // recreate inventory
        this.menuType.create();

        // fill menu
        this.fill();

        // open
        this.open();
    }


    public void nextPage() {
        // add page
        this.curPage++;

        // too large
        if (this.curPage > maxPage) {
            this.curPage = maxPage;
        }

        update();
    }

    public void lastPage() {
        // remove page
        this.curPage--;

        // too large
        if (this.curPage < 1) {
            this.curPage = 1;
        }

        update();
    }

    public void destroy() {
        // unregister listener
        unregisterListener();
    }

    public void close() {
        // clone inventory for player
        if (player.getOpenInventory().getTopInventory().equals(menuType.getInventory())) {
            player.closeInventory();
        }
    }

    // fill task
    public GuiMenu setOnFill(Runnable onFill) {
        this.onFill = onFill;
        return this;
    }

    // items
    public void setItem(ItemStack itemStack, int slot) {
        this.menuType.getInventory().setItem(slot, itemStack);
    }

    public void setItem(GuiItem guiItem, int slot) {
        this.menuType.getInventory().setItem(slot, guiItem.getItemStack());
    }

    public void setItem(Material material, int slot) {
        this.menuType.getInventory().setItem(slot, new GuiItemBuilder().setType(material).setName(" ").create().getItemStack());
    }

    public void setItem(Material material, String name, int slot) {
        this.menuType.getInventory().setItem(slot, new GuiItemBuilder().setType(material).setName(name).create().getItemStack());
    }

    public void addButton(GuiButton button) {
        // add to list
        this.buttons.add(button);

        // add to inventory
        setItem(button.getGuiItem(), button.getSlot());
    }

    public void removeButton(GuiButton button) {
        this.buttons.remove(button);

        // remove from inventory
        setItem((ItemStack) null, button.getSlot());
    }

    // setters

    public GuiMenu setTitle(String title) {
        this.title = title;
        return this;
    }

    // getters

    public int getSize() {
        Validate.isTrue(menuType != null, "MenuType needs to be specified!");
        return this.menuType.getInventory().getSize();
    }

    // event
    @EventHandler
    public void onInventoryEvent(InventoryEvent event) {
        // is current inventory
        if (!event.getInventory().equals(this.menuType.getInventory())) {
            return;
        }

        if (onEventListener != null) onEventListener.onEvent(event, this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // is player
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        // is current player
        if (!this.player.getUniqueId().equals(player.getUniqueId())) {
            return;
        }

        // is current inventory
        if (!event.getInventory().equals(this.menuType.getInventory()) && !event.isShiftClick()) {
            return;
        }

        onClickListener.onClick(event, this);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        // is player
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        // is current player
        if (!this.player.getUniqueId().equals(player.getUniqueId())) {
            return;
        }

        // is current inventory
        if (!event.getInventory().equals(this.menuType.getInventory())) {
            return;
        }

        // item dragged in current inventory
        if (event.getRawSlots().stream().noneMatch(slot -> slot < this.menuType.getInventory().getSize() && slot >= 0)) {
            return;
        }

        onDragListener.onDrag(event, this);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // is player
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        // is current player
        if (!this.player.getUniqueId().equals(player.getUniqueId())) {
            return;
        }

        // is current inventory
        if (!event.getInventory().equals(this.menuType.getInventory())) {
            return;
        }

        onCloseListener.onClose(event, this);
    }

    @EventHandler
    public void onButtonClick(InventoryClickEvent event) {
        // is player
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        // is current player
        if (!this.player.getUniqueId().equals(player.getUniqueId())) {
            return;
        }

        // is current inventory
        if (!event.getInventory().equals(this.menuType.getInventory())) {
            return;
        }

        // get buttons
        for (GuiButton button : buttons) {

            // is slot
            if (event.getRawSlot() != button.getSlot()) {
                continue;
            }

            // cancel event
            event.setCancelled(true);

            // on press
            button.getPressListener().performAction(event.getClick(), this, button, player);

            // sound
            if (button.getPressSound() != null) {
                player.playSound(player.getLocation(), button.getPressSound(), button.getVolume(), button.getPitch());
            }
        }

    }


    // getters

    public String getTitle() {
        return title;
    }

    public int getCurPage() {
        return curPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public Player getPlayer() {
        return player;
    }

    public AGuiMenu getMenuType() {
        return menuType;
    }

    public List<GuiButton> getButtons() {
        return buttons;
    }

    public IEventListener.OnEventListener getOnEventListener() {
        return onEventListener;
    }

    public IEventListener.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public IEventListener.OnDragListener getOnDragListener() {
        return onDragListener;
    }

    public IEventListener.OnCloseListener getOnCloseListener() {
        return onCloseListener;
    }
}
