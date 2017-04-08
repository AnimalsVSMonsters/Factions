package com.animalsvsmonsters.factions.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.animalsvsmonsters.factions.storage.AVMPlayerManager;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/2/2016 at 3:48 PM.
 * ******************************************************************************************
 */
public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        AVMPlayerManager.getManager().removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event){
        AVMPlayerManager.getManager().removePlayer(event.getPlayer());
    }

}
