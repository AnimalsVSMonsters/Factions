package com.animalsvsmonsters.factions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.animalsvsmonsters.factions.callbacks.PlayerLoginCallback;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.team.TeamManager;
import com.animalsvsmonsters.factions.utils.ScoreboardManagement;
import com.animalsvsmonsters.factions.utils.Database.Database;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 1/31/2016 at 9:38 PM.
 * ******************************************************************************************
 */
public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Object[] params = new Object[]{player.getUniqueId().toString()};
        PlayerLoginCallback callback = new PlayerLoginCallback(player);
        Database.get().syncQuery("SELECT * FROM user_info WHERE uuid = ?", params, callback);

        if(AVMPlayerManager.getManager().getPlayer(player).getTeam() == TeamManager.getManager().getTeam("Limbo")){
            player.teleport(AVMPlayerManager.getManager().getPlayer(player).getTeam().getSpawn());
        }

        ScoreboardManagement.getManager().updateAll();
    }

}
