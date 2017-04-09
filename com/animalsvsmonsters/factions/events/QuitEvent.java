package com.animalsvsmonsters.factions.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.animalsvsmonsters.factions.storage.AVMPlayerManager;

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
