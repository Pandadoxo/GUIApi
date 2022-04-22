# How to use *GUIAPI*:
## 1. Download and build the plugin
● Create a new project from Version Control and select this GitHub repository  
● Build the plugin via maven with 'clean install'  
![image](https://user-images.githubusercontent.com/46076002/164503341-c32e65ee-eeb3-41ef-93bc-d44ff13370ab.png)

## 2. Add the plugin dependency to your project
● Go to the pom.xml of your project and add the following dependency:  
  ```xml
<dependency>
      <groupId>de.pandadoxo</groupId>
      <artifactId>GUIApi</artifactId>
      <version>1.0</version>
</dependency>
  ```
  
## 3. Create Items, Buttons and Menus
### GuiItemBuilder
```java
    public GuiItem createExampleItem(String name, String[] lore, Material type) {
        // Create GuiItemBuilder
        GuiItemBuilder itemBuilder = new GuiItemBuilder();
        itemBuilder.setName(name);
        itemBuilder.setLore(lore);
        itemBuilder.setType(type);

        // Create the GuiItem from the itemBuilder and return it
        return itemBuilder.create();
    }
```
  
### GuiButtonBuilder
```java
    public GuiButton createExampleButton(GuiItem guiItem, Runnable onPress) {
        // Create GuiButtonBuilder
        GuiButtonBuilder buttonBuilder = new GuiButtonBuilder();
        buttonBuilder.setGuiItem(guiItem);
        buttonBuilder.setSlot(4); // Set the slot of the button (where it should appear in the inventory)
        buttonBuilder.setPressSound(Sound.BLOCK_NOTE_BLOCK_PLING); // Set the sound of the button
        buttonBuilder.setPitch(1); // Set the pitch of the sound
        buttonBuilder.setVolume(1); // Set the volume of the sound
        buttonBuilder.setPressListener(new PressListener() {
            @Override
            public void performAction(ClickType clickType, GuiMenu guiMenu, GuiButton guiButton, Player player) {
                // Write, what should happen if the button gets pressed here
                onPress.run(); // just an example, you can write code normally

                player.sendMessage("YOU PRESSED A BUTTON :O");
                player.sendMessage("USED BUTTON: " + (clickType.isRightClick() ? "RIGHTCLICK" : "LEFTCLICK"));
                player.sendMessage("THE SLOT OF THE BUTTON IS: " + guiButton.getSlot());
                player.sendMessage("THE MENU'S NAME, IN WHICH THE BUTTON IS, IS: " + guiMenu.getTitle());
            }
        });

        // Create the GuiButton from the buttonBuilder and return it
        return buttonBuilder.create();
    }
```

### GuiMenuBuilder
```jav
    public GuiMenu createExampleMenu(Player player) {
        // Create guiMenuBuilder
        GuiMenuBuilder menuBuilder = new GuiMenuBuilder();
        menuBuilder.setMenuType(GuiChest.class); // !!! Set the type of the menu
        menuBuilder.setPlayer(player);
        menuBuilder.setSize(6 * 9); // This is just important if you use GuiChest

        menuBuilder.setCurPage(1); // The values are just for working purposes, you could also just create variables
        menuBuilder.setMaxPage(2);

        GuiMenu menu = menuBuilder.create(Main.getPlugin(Main.class)); // Create the menu !!! requires the plugin

        // fill the menu
        menu.setOnFill(() -> { // this is just needed, if you want to update the menu frequently (if you want to have a static menu, skip this and directly use menu.setItem etc.!!!)
            // add items with no name (for borders for example)
            for (int i = 0; i < 9; i++) {
                menu.setItem(Material.WHITE_STAINED_GLASS_PANE, i);
            }
            for (int i = 1; i < 6; i++) {
                menu.setItem(Material.WHITE_STAINED_GLASS_PANE, 9 * i);
                menu.setItem(Material.WHITE_STAINED_GLASS_PANE, 9 * i + 8);
            }
            for (int i = 0; i < 9; i++) {
                menu.setItem(Material.WHITE_STAINED_GLASS_PANE, i);
            }

            // add button
            GuiItem item = new GuiItemBuilder().setName("§eI'm a button").setType(Material.STONE_BUTTON).setAmount(32).setLore("§cPress me ^^").create();
            GuiButton button = new GuiButtonBuilder().setGuiItem(item).setSlot(22).setPressListener((clickType, guiMenu, guiButton, player1) -> {
                // send message
                player1.sendMessage("You just pressed a button :HYPERS:");
            }).create();

            menu.addButton(button); // don't forget to actually add the button to the menu
        });

        // fill menu
        menu.fill(); // Only required if you use .setOnFill

        // open the menu
        menu.open();

        return menu;
    }
```

> For further information take a look at [ExampleGuiApi](https://github.com/Pandadoxo/ExampleGuiApi)
