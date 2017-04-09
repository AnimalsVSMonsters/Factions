package com.animalsvsmonsters.factions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

public class MoveEvent implements Listener {

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event){
        Player p = event.getPlayer();
        Hologram hologram = AVMPlayerManager.getManager().getPlayer(p).getHologram();
        hologram.clearLines();
        hologram.appendTextLine(p.getDisplayName());
        hologram.teleport(event.getTo().clone().add(0, 3.5, 0));
    }

}
