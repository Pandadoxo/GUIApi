///////////////////////////////
// Class coded by Pandadoxo  //
// on 23.02.2022 at 11:03     //
// Don't remove this section //
///////////////////////////////
package de.pandadoxo.guiapi.builder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.pandadoxo.guiapi.result.GuiItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;

import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GuiItemBuilder implements Cloneable {

    // item basics
    private Material type;
    private String name;
    private String[] lore = new String[0];
    private int amount = 1;

    // player head
    private String skin;
    private boolean skinIsPlayer;

    // attributes
    private boolean unbreakable;
    private boolean shine;
    private boolean enchantmentsVisible;
    private boolean potionsVisible;

    // armor
    private Color leatherArmorColor;

    // potions
    private Color potionColor;
    private Set<PotionEffect> potionEffects = new HashSet<>();

    // durability
    private int durability = -1;

    // enchantments
    private Map<Enchantment, Integer> enchantments = new HashMap<>();

    public GuiItemBuilder() {
    }

    public GuiItem create() {
        Validate.isTrue(type != null, "Item type needs to be specified!");

        // create item
        ItemStack itemStack = new ItemStack(type);

        // get meta
        ItemMeta meta = itemStack.getItemMeta();

        // basics
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        itemStack.setAmount(amount);

        // player head
        if (meta instanceof SkullMeta skullMeta && skin != null) {
            // is player skin
            if (skinIsPlayer) {
                skullMeta.setOwner(skin);
            }

            // is skin texture
            else {
                // create profile
                skullMeta = addPlayerSkin(skullMeta, skin);
            }
        }

        // attributes
        meta.setUnbreakable(unbreakable);
        if (shine) {
            meta.addEnchant(Enchantment.LUCK, 1, false);
        }
        if (!enchantmentsVisible) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (!potionsVisible) {
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        }

        // armor
        if (meta instanceof LeatherArmorMeta leatherArmorMeta && leatherArmorColor != null) {
            leatherArmorMeta.setColor(leatherArmorColor);
        }

        // potion
        if (meta instanceof PotionMeta potionMeta) {
            // effects
            for (PotionEffect potionEffect : potionEffects) {
                potionMeta.addCustomEffect(potionEffect, true);
            }

            // color
            if (potionColor != null) {
                potionMeta.setColor(potionColor);
            }

        }

        // durability
        if (meta instanceof Damageable damageable && durability > -1) {
            damageable.setDamage(type.getMaxDurability() - durability);
        }

        // enchantments
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }

        // add meta
        itemStack.setItemMeta(meta);

        return new GuiItem(type, name, lore, itemStack);
    }

    private SkullMeta addPlayerSkin(SkullMeta skullMeta, String texture) {
        // create game profile
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        gameProfile.getProperties().put("textures", new Property("textures", texture));

        // insert to field
        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
            profileField.setAccessible(false);
        } catch (Exception ignored) {
        }

        return skullMeta;
    }

    public static GuiItemBuilder fromItemStack(ItemStack itemStack) {
        GuiItemBuilder builder = new GuiItemBuilder();
        if (itemStack == null) {
            return builder;
        }

        // get meta
        ItemMeta meta = itemStack.getItemMeta();

        // item basics
        builder.setType(itemStack.getType());
        builder.setName(meta.getDisplayName());
        builder.setLore(meta.getLore().toArray(new String[]{}));
        builder.setAmount(itemStack.getAmount());

        // player head
        if (meta instanceof SkullMeta skullMeta) {
            // has owner Profile
            PlayerProfile ownerProfile = skullMeta.getOwnerProfile();
            if (ownerProfile != null) {
                builder.setSkin(toBase64(getTextureJson(ownerProfile.getTextures().getSkin())));
                builder.setSkinIsPlayer(false);
            }

            // owner not null
            else if (skullMeta.getOwner() != null) {
                builder.setSkin(skullMeta.getOwner());
                builder.setSkinIsPlayer(true);
            }
        }

        // attributes
        builder.setUnbreakable(meta.isUnbreakable());
        builder.setShine(meta.getEnchantLevel(Enchantment.LUCK) > 0);
        builder.setEnchantmentsVisible(!meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS));
        builder.setPotionsVisible(!meta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS));

        // leather armor
        if (meta instanceof LeatherArmorMeta leatherArmorMeta) {
            builder.setLeatherArmorColor(leatherArmorMeta.getColor());
        }

        // potion
        if (meta instanceof PotionMeta potionMeta) {
            builder.setPotionEffects(new HashSet<>(potionMeta.getCustomEffects()));
            builder.setPotionColor(potionMeta.getColor());
        }

        // durability
        if (meta instanceof Damageable damageable) {
            builder.setDurability(itemStack.getType().getMaxDurability() - damageable.getDamage());
        }

        // enchantments
        builder.addAllEnchantments(itemStack.getEnchantments());

        return builder;
    }

    private static String toBase64(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }

        return Base64.getEncoder().encodeToString(new Gson().toJson(jsonObject).getBytes(StandardCharsets.UTF_8));
    }

    private static JsonObject getTextureJson(URL url) {
        if (url == null) {
            return null;
        }

        JsonObject object = new JsonObject();
        JsonObject textures = new JsonObject();
        JsonObject skin = new JsonObject();
        skin.addProperty("url", url.toString());
        textures.add("SKIN", skin);
        object.add("textures", textures);

        return object;
    }

    // setter
    public GuiItemBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    public GuiItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GuiItemBuilder setLore(String... lore) {
        this.lore = lore;
        return this;
    }

    public GuiItemBuilder addLore(String... toAdd) {
        String[] newArray = Arrays.copyOf(this.lore, this.lore.length + toAdd.length);
        for (int i = this.lore.length; i < newArray.length; i++) {
            newArray[i] = toAdd[i - this.lore.length];
        }

        this.lore = newArray;
        return this;
    }

    public GuiItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public GuiItemBuilder setSkin(String skin) {
        this.skin = skin;
        return this;
    }

    public GuiItemBuilder setSkinIsPlayer(boolean skinIsPlayer) {
        this.skinIsPlayer = skinIsPlayer;
        return this;
    }

    public GuiItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public GuiItemBuilder setShine(boolean shine) {
        this.shine = shine;
        if (shine) {
            this.enchantmentsVisible = false;
        }
        return this;
    }

    public GuiItemBuilder setEnchantmentsVisible(boolean enchantmentsVisible) {
        this.enchantmentsVisible = enchantmentsVisible;
        return this;
    }

    public GuiItemBuilder setPotionsVisible(boolean potionsVisible) {
        this.potionsVisible = potionsVisible;
        return this;
    }

    public GuiItemBuilder setLeatherArmorColor(Color leatherArmorColor) {
        this.leatherArmorColor = leatherArmorColor;
        return this;
    }

    public GuiItemBuilder setPotionColor(Color potionColor) {
        this.potionColor = potionColor;
        return this;
    }

    public GuiItemBuilder setPotionEffects(Set<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
        return this;
    }

    public GuiItemBuilder addPotionAffect(PotionEffect... potionEffects) {
        this.potionEffects.addAll(Arrays.stream(potionEffects).toList());
        return this;
    }

    public GuiItemBuilder removePotionEffect(PotionEffectType... potionEffectTypes) {
        this.potionEffects.removeIf(potionEffect -> Arrays.asList(potionEffectTypes).contains(potionEffect.getType()));
        return this;
    }

    public GuiItemBuilder setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public GuiItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public GuiItemBuilder addAllEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments.putAll(enchantments);
        return this;
    }


    @Override
    public GuiItemBuilder clone() {
        try {
            GuiItemBuilder clone = (GuiItemBuilder) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
