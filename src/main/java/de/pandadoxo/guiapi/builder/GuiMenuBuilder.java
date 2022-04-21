///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:07     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.builder;

import de.pandadoxo.guiapi.interfaces.AGuiMenu;
import de.pandadoxo.guiapi.interfaces.IEventListener;
import de.pandadoxo.guiapi.result.GuiButton;
import de.pandadoxo.guiapi.result.GuiMenu;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GuiMenuBuilder implements Listener {

    private final List<GuiButton> buttons = new ArrayList<>();

    private Player player;
    private Class<? extends AGuiMenu> menuType;
    private String title;
    private int size;
    private int curPage;
    private int maxPage;

    private IEventListener.OnEventListener onEventListener = IEventListener.DefaultOnEventListener();
    private IEventListener.OnClickListener onClickListener = IEventListener.DefaultOnClickListener();
    private IEventListener.OnDragListener onDragListener = IEventListener.DefaultOnDragListener();
    private IEventListener.OnCloseListener onCloseListener = IEventListener.DefaultOnCloseListener();

    public GuiMenuBuilder() {
        this.curPage = 1;
        this.maxPage = 1;
    }

    public GuiMenuBuilder(Player player) {
        this.player = player;
        this.curPage = 1;
        this.maxPage = 1;
    }

    public GuiMenuBuilder(Player player, Class<? extends AGuiMenu> clazz, String title) {
        this.player = player;
        this.menuType = clazz;
        this.title = title;
        this.curPage = 1;
        this.maxPage = 1;
    }

    public GuiMenu create(Plugin plugin) {
        Validate.isTrue(player != null, "Player needs to be specified!");
        Validate.isTrue(menuType != null, "Menu-Type needs to be specified!");

        try {
            return new GuiMenu(plugin, player, menuType, title, size, curPage, maxPage, buttons, onEventListener, onClickListener, onDragListener, onCloseListener);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    // setter
    public GuiMenuBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public GuiMenuBuilder setMenuType(Class<? extends AGuiMenu> menuType) {
        this.menuType = menuType;
        return this;
    }

    public GuiMenuBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public GuiMenuBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public GuiMenuBuilder setCurPage(int curPage) {
        this.curPage = curPage;
        return this;
    }

    public GuiMenuBuilder setMaxPage(int maxPage) {
        this.maxPage = maxPage;
        return this;
    }

    public GuiMenuBuilder setOnEventListener(IEventListener.OnEventListener onEventListener) {
        this.onEventListener = onEventListener;
        return this;
    }

    public GuiMenuBuilder setOnClickListener(IEventListener.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public GuiMenuBuilder setOnDragListener(IEventListener.OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
        return this;
    }

    public GuiMenuBuilder setOnCloseListener(IEventListener.OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
        return this;
    }

    // event methods
    public void onClick(IEventListener.OnClickListener listener) {
        this.onClickListener = listener;
    }

    public void onDrag(IEventListener.OnDragListener listener) {
        this.onDragListener = listener;
    }

    public void onClose(IEventListener.OnCloseListener listener) {
        this.onCloseListener = listener;
    }

    public void onEvent(IEventListener.OnEventListener listener) {
        this.onEventListener = listener;
    }

}
