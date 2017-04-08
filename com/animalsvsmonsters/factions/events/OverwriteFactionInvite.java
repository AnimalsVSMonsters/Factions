package com.animalsvsmonsters.factions.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.utils.Lang;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/1/2016 at 3:53 PM.
 * ******************************************************************************************
 */
public class OverwriteFactionInvite implements Listener {

    @EventHandler
    public void onFactionInvite(PlayerCommandPreprocessEvent event){
        if(event.getMessage().contains("/faction invite ") || event.getMessage().contains("/f invite ")){
            String message = event.getMessage();
            String[] args = message.split(" ");
            String playerName = args[2];
            if(AVMPlayerManager.getManager().getPlayer(playerName) != null){
                AVMPlayer p = AVMPlayerManager.getManager().getPlayer(playerName);
                if(p.getTeam() != AVMPlayerManager.getManager().getPlayer(event.getPlayer()).getTeam()){
                    event.getPlayer().sendMessage(Lang.TEAM_DENY.toString());
                    event.setCancelled(true);
                }
            }else{
                event.getPlayer().sendMessage("§cCould not find player '§6" + playerName + "§c'");
                event.setCancelled(true);
            }
        }
    }

}
