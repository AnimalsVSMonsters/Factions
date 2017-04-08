package com.animalsvsmonsters.factions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/3/2016 at 8:19 PM.
 * ******************************************************************************************
 */
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
