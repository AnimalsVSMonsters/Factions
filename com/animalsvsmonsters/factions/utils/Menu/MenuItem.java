package com.animalsvsmonsters.factions.utils.Menu;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/2/2016 at 1:14 PM.
 * ******************************************************************************************
 */
public abstract class MenuItem
{
    private Menu menu;
    private int number;
    private MaterialData icon;
    private ItemMeta savedMeta;
    private String text;
    private int amount = 1;
    private Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
    private List<String> descriptions = new ArrayList<String>();

    public MenuItem(ItemStack stack) {}

    public MenuItem(String text)
    {
        this(text, new MaterialData(Material.PAPER));
    }

    public MenuItem(String text, MaterialData icon)
    {
        this(text, icon, 1);
    }

    public MenuItem(String text, MaterialData icon, int number)
    {
        this.text = text;
        this.icon = icon;
        this.number = number;
    }

    protected void addToMenu(Menu menu)
    {
        this.menu = menu;
    }

    protected void removeFromMenu(Menu menu)
    {
        if (this.menu == menu) {
            this.menu = null;
        }
    }

    public Menu getMenu()
    {
        return this.menu;
    }

    public int getNumber()
    {
        return this.number;
    }

    public MaterialData getIcon()
    {
        return this.icon;
    }

    public String getText()
    {
        return this.text;
    }

    public void setDescriptions(List<String> lines)
    {
        this.descriptions = lines;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public void addDescription(String line)
    {
        this.descriptions.add(line);
    }

    public void addEnchantments(Map<Enchantment, Integer> enchs)
    {
        this.enchantments.putAll(enchs);
    }

    protected ItemStack getItemStack()
    {
        @SuppressWarnings("deprecation")
		ItemStack slot = new ItemStack(getIcon().getItemType(), getNumber(), getIcon().getData());
        slot.setAmount(this.amount);
        slot.addUnsafeEnchantments(this.enchantments);
        if (this.savedMeta == null)
        {
            ItemMeta meta = slot.getItemMeta();
            meta.setLore(this.descriptions);
            meta.setDisplayName(getText());
            slot.setItemMeta(meta);
        }
        else
        {
            slot.setItemMeta(getSavedMeta());
        }
        return slot;
    }

    public abstract void onClick(Player paramPlayer);

    public ItemMeta getSavedMeta()
    {
        return this.savedMeta;
    }

    public void setSavedMeta(ItemMeta savedMeta)
    {
        this.savedMeta = savedMeta;
    }
}

