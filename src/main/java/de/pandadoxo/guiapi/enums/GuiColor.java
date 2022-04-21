///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:10     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.enums;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

public enum GuiColor {

    BLACK(ChatColor.BLACK, Color.fromBGR(0, 0, 0), Material.BLACK_WOOL, Material.BLACK_BED, Material.BLACK_STAINED_GLASS_PANE),
    DARK_BLUE(ChatColor.DARK_BLUE, Color.fromBGR(0, 0, 170), Material.BLUE_WOOL, Material.BLUE_BED, Material.BLUE_STAINED_GLASS_PANE),
    DARK_GREEN(ChatColor.DARK_GREEN, Color.fromBGR(0, 170, 0), Material.GREEN_WOOL, Material.GREEN_BED, Material.GREEN_STAINED_GLASS_PANE),
    DARK_AQUA(ChatColor.DARK_AQUA, Color.fromBGR(0, 170, 170), Material.CYAN_WOOL, Material.CYAN_BED, Material.CYAN_STAINED_GLASS_PANE),
    DARK_RED(ChatColor.DARK_RED, Color.fromBGR(170, 0, 0), Material.RED_WOOL, Material.RED_BED, Material.RED_STAINED_GLASS_PANE),
    DARK_PURPLE(ChatColor.DARK_PURPLE, Color.fromBGR(170, 0, 170), Material.PURPLE_WOOL, Material.PURPLE_BED, Material.PURPLE_STAINED_GLASS_PANE),
    GOLD(ChatColor.GOLD, Color.fromBGR(255, 170, 0), Material.ORANGE_WOOL, Material.ORANGE_BED, Material.ORANGE_STAINED_GLASS_PANE),
    GRAY(ChatColor.GRAY, Color.fromBGR(170, 170, 170), Material.GRAY_WOOL, Material.GRAY_BED, Material.GRAY_STAINED_GLASS_PANE),
    DARK_GRAY(ChatColor.DARK_GRAY, Color.fromBGR(85, 85, 85), Material.GRAY_WOOL, Material.GRAY_BED, Material.GRAY_STAINED_GLASS_PANE),
    BLUE(ChatColor.BLUE, Color.fromBGR(85, 85, 255), Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_BED, Material.LIGHT_BLUE_STAINED_GLASS_PANE),
    GREEN(ChatColor.GREEN, Color.fromBGR(85, 255, 85), Material.LIME_WOOL, Material.LIME_BED, Material.LIME_STAINED_GLASS_PANE),
    AQUA(ChatColor.AQUA, Color.fromBGR(85, 255, 255), Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_BED, Material.LIGHT_BLUE_STAINED_GLASS_PANE),
    RED(ChatColor.RED, Color.fromBGR(255, 85, 85), Material.RED_WOOL, Material.RED_BED, Material.RED_STAINED_GLASS_PANE),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, Color.fromBGR(255, 85, 255), Material.MAGENTA_WOOL, Material.MAGENTA_BED, Material.MAGENTA_STAINED_GLASS_PANE),
    YELLOW(ChatColor.YELLOW, Color.fromBGR(255, 255, 85), Material.YELLOW_WOOL, Material.YELLOW_BED, Material.YELLOW_STAINED_GLASS_PANE),
    WHITE(ChatColor.WHITE, Color.fromBGR(255, 255, 255), Material.WHITE_WOOL, Material.WHITE_BED, Material.WHITE_STAINED_GLASS_PANE);

    private final ChatColor chatColor;
    private final Color color;
    private final Material wool;
    private final Material bed;
    private final Material glassPane;

    GuiColor(ChatColor chatColor, Color color, Material wool, Material bed, Material glassPane) {
        this.chatColor = chatColor;
        this.color = color;
        this.wool = wool;
        this.bed = bed;
        this.glassPane = glassPane;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Color getColor() {
        return color;
    }

    public Material getWool() {
        return wool;
    }

    public Material getBed() {
        return bed;
    }

    public Material getGlassPane() {
        return glassPane;
    }

    public static GuiColor getFromChatColor(ChatColor chatColor) {
        for (GuiColor value : GuiColor.values()) {
            if(value.getChatColor().equals(chatColor)) {
                return value;
            }
        }

        return null;
    }

    public static GuiColor getFromWool(Material wool) {
        for (GuiColor value : GuiColor.values()) {
            if(value.getWool().equals(wool)) {
                return value;
            }
        }

        return null;
    }

    public static GuiColor getFromBed(Material bed) {
        for (GuiColor value : GuiColor.values()) {
            if(value.getBed().equals(bed)) {
                return value;
            }
        }

        return null;
    }

    public static GuiColor getFromGlassPane(Material glassPane) {
        for (GuiColor value : GuiColor.values()) {
            if(value.getGlassPane().equals(glassPane)) {
                return value;
            }
        }

        return null;
    }
}
