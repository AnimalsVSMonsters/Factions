package com.animalsvsmonsters.factions.utils;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder{

    private ItemStack defaultItem;
    private ItemMeta defaultMeta;

    public ItemBuilder(ItemStack item) {
        this.defaultItem = item;
        this.defaultMeta = defaultItem.getItemMeta();
    }

    public ItemBuilder(Material material){
        this.defaultItem = new ItemStack(material);
        this.defaultMeta = defaultItem.getItemMeta();
    }

    public ItemBuilder type(Material material){
        defaultItem.setType(material);
        return this;
    }

    public ItemBuilder amount(int amount){
        defaultItem.setAmount(amount);
        return this;
    }

    public ItemBuilder durability(int durability){
        defaultItem.setDurability((short) durability);
        return this;
    }

    @SuppressWarnings("deprecation")
	public ItemBuilder data(int data){
        defaultItem.setData(new MaterialData(defaultItem.getType(), (byte) data));
        return this;
    }

    public ItemBuilder name(String name){
        defaultMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder resetName(){
        defaultMeta.setDisplayName(WordUtils.capitalize(defaultItem.getType().name().replace("_", "")));
        return this;
    }

    public ItemBuilder lore(List<String> lore){
        defaultMeta.setLore(lore);
        return this;
    }

    public ItemBuilder lore(String... lore){
        List<String> itemLore = defaultMeta.getLore();
        if(itemLore == null) itemLore = new ArrayList<String>();
        itemLore.addAll(Arrays.asList(lore));
        defaultMeta.setLore(itemLore);
        return this;
    }

    public ItemBuilder removeLoreLine(String line){
        List<String> lore = defaultMeta.getLore();
        if(lore.contains(line)) lore.remove(line);
        defaultMeta.setLore(lore);
        return this;
    }

    public ItemBuilder clearLore(){
        defaultMeta.setLore(new ArrayList<String>());
        return this;
    }

    public ItemBuilder enchant(Enchantment enchant){
        defaultMeta.addEnchant(enchant, 1, false);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchant, int level){
        defaultMeta.addEnchant(enchant, level, false);
        return this;
    }

    public ItemStack build(){
        defaultItem.setItemMeta(defaultMeta);
        return defaultItem;
    }



}
