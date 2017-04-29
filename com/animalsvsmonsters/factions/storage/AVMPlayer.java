package com.animalsvsmonsters.factions.storage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.animalsvsmonsters.factions.Main;
import com.animalsvsmonsters.factions.events.ArmorUtil;
import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.kit.KitManager;
import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.team.TeamManager;
import com.animalsvsmonsters.factions.utils.ScoreboardManagement;
import com.animalsvsmonsters.factions.utils.database.Database;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import me.libraryaddict.disguise.DisguiseAPI;

public class AVMPlayer {

	private Player player;
	private Team team;
	private Kit kit;
	private Hologram hologram;

	private boolean updating = false;

	public AVMPlayer(final Player player) {
		this.player = player;
		AVMPlayerManager.getManager().getPlayerHashMap().put(player, this);
		hologram = HologramsAPI.createHologram(Main.getInstance(), player.getLocation().add(0, 2.5, 0));
		hologram.appendTextLine(player.getDisplayName());
		if (!updating) {
			updating = true;
			new BukkitRunnable() {
				@Override
				public void run() {
					ArmorUtil.updateAll();
				}
			}.runTaskTimer(Main.getInstance(), 0, 5);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public Team getTeam() {
		return team;
	}

	public Kit getKit() {
		return kit;
	}

	public void setTeam(Team team) {
		this.team = team;
		team.addMember(this);
		if (team == TeamManager.getManager().getTeam("Limbo"))
			getPlayer().teleport(team.getSpawn());
		assert !team.getTeamName().equalsIgnoreCase("Limbo");
		Database.get().syncUpdate("UPDATE user_info SET team = ? WHERE uuid = ?",
				new Object[] { team.getTeamName(), player.getUniqueId().toString() });
	}

	public void setKit(Kit kit) {
		this.kit = kit;
		if (kit == KitManager.getManager().getKit("default")) {
			if (DisguiseAPI.isSelfDisguised(player))
				DisguiseAPI.getDisguise(player).removeDisguise();
			return;
		}
		DisguiseAPI.disguiseToAll(player, kit.getDisguise());
		Database.get().syncUpdate("UPDATE user_info SET kit = ? WHERE uuid = ?",
				new Object[] { kit.getKitNumber(), player.getUniqueId().toString() });
		if (kit == KitManager.getManager().getKit("Rabbit")) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 2, false, false));
		} else {
			player.removePotionEffect(PotionEffectType.JUMP);
		}
		ScoreboardManagement.getManager().updateAll();
	}

	public Hologram getHologram() {
		return hologram;
	}

	public void reset(int result) {
		if (AVMPlayerManager.getManager().hasReset(this) && getTeam() != TeamManager.getManager().getTeam("Limbo")) {
			getTeam().removeMember(this);
			Object[] params = new Object[] { player.getUniqueId().toString(), result };
			for (ItemStack itemStack : player.getInventory()) {
				assert itemStack != null;
				player.getInventory().remove(itemStack);
			}
			for (ItemStack itemStack : player.getInventory().getArmorContents()) {
				assert itemStack != null;
				player.getInventory().remove(itemStack);
			}
			FPlayer p = FPlayers.getInstance().getByPlayer(player);
			if (p.getFaction() != null) {
				p.getFaction().removeFPlayer(p);
			}
			setTeam(TeamManager.getManager().getTeam("Limbo"));
			setKit(KitManager.getManager().getKit("default"));
			Database.get().syncUpdate("UPDATE user_info SET kit = ? WHERE uuid = ?",
					new Object[] { 0, player.getUniqueId().toString() });
			Database.get().syncUpdate("DELETE FROM reset_queue WHERE uuid = ? AND resetkey = ?", params);
			AVMPlayerManager.getManager().removeReset(this);
			ScoreboardManagement.getManager().updateAll();
		}
	}
}
