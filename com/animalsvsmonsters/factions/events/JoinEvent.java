package com.animalsvsmonsters.factions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.animalsvsmonsters.factions.callbacks.PlayerLoginCallback;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.team.TeamManager;
import com.animalsvsmonsters.factions.utils.ScoreboardManagement;
import com.animalsvsmonsters.factions.utils.database.Database;

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
