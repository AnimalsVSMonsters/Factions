package com.animalsvsmonsters.factions.events;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.kit.KitManager;

public class RespawnEvent implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Kit kit = KitManager.getManager().getKit(event.getPlayer());
        if(kit != KitManager.getManager().getKit("default"))
            DisguiseAPI.disguiseToAll(event.getPlayer(), kit.getDisguise());
    }

}
