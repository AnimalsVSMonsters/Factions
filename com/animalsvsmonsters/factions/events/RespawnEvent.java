package com.animalsvsmonsters.factions.events;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.kit.KitManager;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/3/2016 at 1:12 PM.
 * ******************************************************************************************
 */
public class RespawnEvent implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Kit kit = KitManager.getManager().getKit(event.getPlayer());
        if(kit != KitManager.getManager().getKit("default"))
            DisguiseAPI.disguiseToAll(event.getPlayer(), kit.getDisguise());
    }

}
