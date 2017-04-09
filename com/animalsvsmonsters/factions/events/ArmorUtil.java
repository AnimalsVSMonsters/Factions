package com.animalsvsmonsters.factions.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;

import java.util.Arrays;
import java.util.List;

public class ArmorUtil implements Listener {

	public static void updateAll() {
		for (AVMPlayer p : AVMPlayerManager.getManager().getPlayerHashMap().values()) {
			Player player = p.getPlayer();
			ChatColor chatColor = getDisplayColor(player.getInventory().getHelmet(),
					player.getInventory().getChestplate(), player.getInventory().getLeggings(),
					player.getInventory().getBoots());
			player.setDisplayName(chatColor + player.getName() + ChatColor.RESET);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		ChatColor chatColor = getDisplayColor(player.getInventory().getHelmet(), player.getInventory().getChestplate(),
				player.getInventory().getLeggings(), player.getInventory().getBoots());
		player.setDisplayName(chatColor + player.getDisplayName() + ChatColor.RESET);
	}

	private static ArmorPart getArmorPart(ItemStack itemStack) {
		if (itemStack.getType() == Material.LEATHER_HELMET)
			return ArmorPart.L_HELMET;
		if (itemStack.getType() == Material.LEATHER_CHESTPLATE)
			return ArmorPart.L_CHESTPLATE;
		if (itemStack.getType() == Material.LEATHER_LEGGINGS)
			return ArmorPart.L_LEGGINGS;
		if (itemStack.getType() == Material.LEATHER_BOOTS)
			return ArmorPart.L_BOOTS;
		if (itemStack.getType() == Material.IRON_HELMET)
			return ArmorPart.I_HELMET;
		if (itemStack.getType() == Material.IRON_CHESTPLATE)
			return ArmorPart.I_CHESTPLATE;
		if (itemStack.getType() == Material.IRON_LEGGINGS)
			return ArmorPart.I_LEGGINGS;
		if (itemStack.getType() == Material.IRON_BOOTS)
			return ArmorPart.I_BOOTS;
		if (itemStack.getType() == Material.GOLD_HELMET)
			return ArmorPart.G_HELMET;
		if (itemStack.getType() == Material.GOLD_CHESTPLATE)
			return ArmorPart.G_CHESTPLATE;
		if (itemStack.getType() == Material.GOLD_LEGGINGS)
			return ArmorPart.G_LEGGINGS;
		if (itemStack.getType() == Material.GOLD_BOOTS)
			return ArmorPart.G_BOOTS;
		if (itemStack.getType() == Material.CHAINMAIL_HELMET)
			return ArmorPart.C_HELMET;
		if (itemStack.getType() == Material.CHAINMAIL_CHESTPLATE)
			return ArmorPart.C_CHESTPLATE;
		if (itemStack.getType() == Material.CHAINMAIL_LEGGINGS)
			return ArmorPart.C_LEGGINGS;
		if (itemStack.getType() == Material.CHAINMAIL_BOOTS)
			return ArmorPart.C_BOOTS;
		if (itemStack.getType() == Material.DIAMOND_HELMET)
			return ArmorPart.D_HELMET;
		if (itemStack.getType() == Material.DIAMOND_CHESTPLATE)
			return ArmorPart.D_CHESTPLATE;
		if (itemStack.getType() == Material.DIAMOND_LEGGINGS)
			return ArmorPart.D_LEGGINGS;
		if (itemStack.getType() == Material.DIAMOND_BOOTS)
			return ArmorPart.D_BOOTS;
		return null;
	}

	private static ChatColor getDisplayColor(ItemStack helmet, ItemStack chestplate, ItemStack leggings,
			ItemStack boots) {
		ArmorPart h = null;
		ArmorPart c = null;
		ArmorPart l = null;
		ArmorPart b = null;
		if (helmet != null) {
			h = getArmorPart(helmet);
		}
		if (chestplate != null) {
			c = getArmorPart(chestplate);
		}
		if (leggings != null) {
			l = getArmorPart(leggings);
		}
		if (boots != null) {
			b = getArmorPart(boots);
		}
		List<ArmorPart> parts = Arrays.asList(h, c, l, b);
		double leather = 0;
		double iron = 0;
		double gold = 0;
		double chain = 0;
		double diamond = 0;
		for (ArmorPart a : parts) {
			if (a != null) {
				if (a.getType() == ArmorType.LEATHER)
					leather += a.getArmorBars();
				if (a.getType() == ArmorType.IRON)
					iron += a.getArmorBars();
				if (a.getType() == ArmorType.GOLD)
					gold += a.getArmorBars();
				if (a.getType() == ArmorType.CHAIN)
					chain += a.getArmorBars();
				if (a.getType() == ArmorType.DIAMOND)
					diamond += a.getArmorBars();
			}
		}
		if (leather != 0 || iron != 0 || gold != 0 || chain != 0 || diamond != 0) {
			if (leather > iron) {
				if (leather > gold) {
					if (leather > chain) {
						if (leather > diamond) {
							return ChatColor.GOLD;
						} else {
							return ChatColor.AQUA;
						}
					} else {
						if (chain > diamond) {
							return ChatColor.DARK_GREEN;
						} else {
							return ChatColor.AQUA;
						}
					}
				} else {
					if (gold > chain) {
						if (gold > diamond) {
							return ChatColor.YELLOW;
						} else {
							return ChatColor.AQUA;
						}
					} else {
						if (chain > diamond) {
							return ChatColor.DARK_GREEN;
						} else {
							return ChatColor.AQUA;
						}
					}
				}
			} else {
				if (iron > gold) {
					if (iron > chain) {
						if (iron > diamond) {
							return ChatColor.GRAY;
						} else {
							return ChatColor.AQUA;
						}
					} else {
						if (chain > diamond) {
							return ChatColor.DARK_GREEN;
						} else {
							return ChatColor.AQUA;
						}
					}
				} else {
					if (gold > chain) {
						if (gold > diamond) {
							return ChatColor.YELLOW;
						} else {
							return ChatColor.AQUA;
						}
					} else {
						if (chain > diamond) {
							return ChatColor.DARK_GREEN;
						} else {
							return ChatColor.AQUA;
						}
					}
				}
			}
		}
		return ChatColor.RESET;
	}

	private enum ArmorType {
		LEATHER, IRON, GOLD, CHAIN, DIAMOND
	}

	private enum ArmorPart {
		L_HELMET(ArmorType.LEATHER, 1), L_CHESTPLATE(ArmorType.LEATHER, 3), L_LEGGINGS(ArmorType.LEATHER, 2), L_BOOTS(
				ArmorType.LEATHER,
				1), I_HELMET(ArmorType.IRON, 2), I_CHESTPLATE(ArmorType.IRON, 6), I_LEGGINGS(ArmorType.IRON,
						2), I_BOOTS(ArmorType.IRON, 1), G_HELMET(ArmorType.GOLD, 2), G_CHESTPLATE(ArmorType.GOLD,
								5), G_LEGGINGS(ArmorType.GOLD, 3), G_BOOTS(ArmorType.GOLD, 1), C_HELMET(ArmorType.CHAIN,
										2), C_CHESTPLATE(ArmorType.CHAIN, 5), C_LEGGINGS(ArmorType.CHAIN,
												4), C_BOOTS(ArmorType.CHAIN, 1), D_HELMET(ArmorType.DIAMOND,
														3), D_CHESTPLATE(ArmorType.DIAMOND, 8), D_LEGGINGS(
																ArmorType.DIAMOND, 6), D_BOOTS(ArmorType.DIAMOND, 3);

		ArmorType type;
		double armorBars;

		ArmorPart(ArmorType type, double armorBars) {
			this.type = type;
			this.armorBars = armorBars;
		}

		public ArmorType getType() {
			return type;
		}

		public double getArmorBars() {
			return armorBars;
		}
	}

}
