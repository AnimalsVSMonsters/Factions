package com.animalsvsmonsters.factions.utils.Menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;

/**
 * *
 * *****************************************************************************
 * ********* Copyright MikeTheDev (c) 2016. All Rights Reserved. Any code
 * contained within AVMFactions (this document), and any associated APIs with
 * similar branding are the sole property of Michael Petramalo. Distribution,
 * reproduction, taking sections, or claiming any contents as your own will
 * break the terms of the license, and void any agreements with the third-party,
 * you. Thanks. Created on 2/2/2016 at 1:13 PM.
 * *****************************************************************************
 * *************
 */
public class Menu implements InventoryHolder {
	private HashMap<Integer, MenuItem> items = new HashMap<Integer, MenuItem>();
	private Inventory inventory;
	private String title;
	private InventoryHolder holder;
	private int rows;
	private boolean exitOnClickOutside = true;
	private MenuCloseBehaviour menuCloseBehaviour;

	public Menu(String title, int rows, InventoryHolder holder) {
		this.title = title;
		this.rows = rows;
		this.holder = holder;
	}

	public void setMenuCloseBehaviour(MenuCloseBehaviour menuCloseBehaviour) {
		this.menuCloseBehaviour = menuCloseBehaviour;
	}

	public MenuCloseBehaviour getMenuCloseBehaviour() {
		return this.menuCloseBehaviour;
	}

	public void setExitOnClickOutside(boolean exit) {
		this.exitOnClickOutside = exit;
	}

	public boolean addMenuItem(MenuItem item, int x, int y) {
		return addMenuItem(item, y * 9 + x);
	}

	public boolean addMenuItem(MenuItem item, int index) {
		ItemStack slot = getInventory().getItem(index);
		if ((slot != null) && (slot.getType() != Material.AIR)) {
			return false;
		}
		getInventory().setItem(index, item.getItemStack());
		this.items.put(Integer.valueOf(index), item);
		item.addToMenu(this);
		return true;
	}

	public boolean removeMenuItem(int x, int y) {
		return removeMenuItem(y * 9 + x);
	}

	public boolean removeMenuItem(int index) {
		ItemStack slot = getInventory().getItem(index);
		if ((slot == null) || (slot.getType() == Material.AIR)) {
			return false;
		}
		getInventory().clear(index);
		((MenuItem) this.items.remove(Integer.valueOf(index))).removeFromMenu(this);
		return true;
	}

	protected void selectMenuItem(Player player, int index) {
		if (this.items.containsKey(Integer.valueOf(index))) {
			MenuItem item = (MenuItem) this.items.get(Integer.valueOf(index));
			item.onClick(player);
		}
	}

	public void openMenu(Player player) {
		if (getInventory().getViewers().contains(player)) {
			throw new IllegalArgumentException(player.getName() + " is already viewing " + getInventory().getTitle());
		}
		player.openInventory(getInventory());
	}

	public void closeMenu(Player player) {
		if (getInventory().getViewers().contains(player)) {
			getInventory().getViewers().remove(player);
			player.closeInventory();
		}
	}

	public void switchMenu(Player player, Menu toMenu) {
		MenuAPI.switchMenu(player, this, toMenu);
	}

	public Inventory getInventory() {
		if (this.inventory == null) {
			this.inventory = Bukkit.createInventory(this, this.rows * 9, this.title);
		}
		return this.inventory;
	}

	public boolean exitOnClickOutside() {
		return this.exitOnClickOutside;
	}

	protected Menu clone() {
		Menu clone = new Menu(this.title, this.rows, this.holder);
		clone.setExitOnClickOutside(this.exitOnClickOutside);
		clone.setMenuCloseBehaviour(this.menuCloseBehaviour);
		for (@SuppressWarnings("rawtypes")
		Iterator localIterator = this.items.keySet().iterator(); localIterator.hasNext();) {
			int index = (Integer) localIterator.next();
			addMenuItem((MenuItem) this.items.get(Integer.valueOf(index)), index);
		}
		return clone;
	}

	public void updateMenu() {
		for (HumanEntity entity : getInventory().getViewers()) {
			if ((entity instanceof Player)) {
				Player player = (Player) entity;
				player.updateInventory();
			}
		}
	}

	public InventoryHolder getHolder() {
		return this.holder;
	}

	public void setHolder(InventoryHolder holder) {
		this.holder = holder;
	}
}
