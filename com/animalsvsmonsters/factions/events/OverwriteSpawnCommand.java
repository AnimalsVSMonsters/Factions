package com.animalsvsmonsters.factions.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.animalsvsmonsters.factions.Main;
import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.utils.Lang;

import java.util.ArrayList;
import java.util.List;

public class OverwriteSpawnCommand implements Listener {

	private List<AVMPlayer> teleporting = new ArrayList<AVMPlayer>();

	@EventHandler
	public void onSpawnCommandEvent(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase("/spawn")) {
			event.setCancelled(true);
			final AVMPlayer player = AVMPlayerManager.getManager().getPlayer(event.getPlayer());
			final Team team = player.getTeam();
			int delay = Main.getInstance().getConfiguration().getInt("spawn_delay");
			if (delay != 0) {
				player.getPlayer().sendMessage("§6You will be teleported to your spawn in " + delay);
				teleporting.add(player);
				new BukkitRunnable() {
					@Override
					public void run() {
						if (teleporting.contains(player)) {
							player.getPlayer().teleport(team.getSpawn());
							player.getPlayer().sendMessage(Lang.TELEPORT.toString());
							teleporting.remove(player);
						}
					}
				}.runTaskLater(Main.getInstance(), delay * 20);
			} else {
				player.getPlayer().teleport(team.getSpawn());
				player.getPlayer().sendMessage(Lang.TELEPORT.toString());
			}
		}
	}

	@EventHandler
	public void onMoveEvent(PlayerMoveEvent event) {
		if (teleporting.contains(AVMPlayerManager.getManager().getPlayer(event.getPlayer()))) {
			Location to = event.getTo();
			Location from = event.getFrom();
			if (to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ()) {
				teleporting.remove(AVMPlayerManager.getManager().getPlayer(event.getPlayer()));
				event.getPlayer().sendMessage("§cTeleportation was cancelled because you moved!");
			}
		}
	}

}
