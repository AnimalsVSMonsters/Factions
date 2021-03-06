package com.animalsvsmonsters.factions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.utils.ScoreboardManagement;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player killed = event.getEntity();
        Player killer = killed.getKiller();

        if(killer == null) return;

        Team tKilled = AVMPlayerManager.getManager().getPlayer(killed).getTeam();
        Team tKiller = AVMPlayerManager.getManager().getPlayer(killer).getTeam();

        assert tKilled != null;
        assert tKiller != null;

        if(tKilled != tKiller){
            tKilled.addDeath();
            tKiller.addKill();
            ScoreboardManagement.getManager().updateAll();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            AVMPlayer e = AVMPlayerManager.getManager().getPlayer((Player) event.getEntity());
            AVMPlayer d = AVMPlayerManager.getManager().getPlayer((Player) event.getDamager());

            if(e.getKit() == d.getKit())
                event.setCancelled(true);
        }
    }

}
