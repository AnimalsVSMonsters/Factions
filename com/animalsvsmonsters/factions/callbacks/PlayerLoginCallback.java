package com.animalsvsmonsters.factions.callbacks;

import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.kit.KitManager;
import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.team.TeamManager;
import com.animalsvsmonsters.factions.utils.Database.Callback;
import com.animalsvsmonsters.factions.utils.Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 1/31/2016 at 9:56 PM.
 * ******************************************************************************************
 */
public class PlayerLoginCallback implements Callback {

    public Player player;

    private AVMPlayer avmPlayer;

    private boolean inTheTable = false;

    public PlayerLoginCallback(Player player){
        this.player = player;
    }

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(rs.next()){
            String tn = rs.getString("team");
            Team team;
            Kit kit;
            if(!tn.equalsIgnoreCase("None")){
                team = TeamManager.getManager().getTeam(tn);
                kit = KitManager.getManager().getKit(team, rs.getInt("kit"));
                inTheTable = true;
            }else{
                team = TeamManager.getManager().getTeam("Limbo");
                kit = KitManager.getManager().getKit("default");
                inTheTable = true;
            }
            this.avmPlayer = new AVMPlayer(player);
            avmPlayer.setTeam(team);
            avmPlayer.setKit(kit);
        }
    }

    @Override
    public void digestSync() {
        if(!inTheTable){
            Object[] params = new Object[] {player.getUniqueId().toString(), "None", 0};
            Database.get().syncUpdate("INSERT INTO user_info (uuid, team, kit) VALUES (?, ?, ?)", params);
            params = new Object[] {player.getUniqueId().toString()};
            Database.get().syncQuery("SELECT * FROM user_info WHERE uuid = ?", params, this);
            avmPlayer.setTeam(TeamManager.getManager().getTeam("Limbo"));
            avmPlayer.setKit(KitManager.getManager().getKit("default"));
        }
        Object[] params = new Object[]{player.getUniqueId().toString()};
        Callback callback = new ResetQueueCallback(player);
        Database.get().syncQuery("SELECT resetkey FROM reset_queue WHERE uuid = ?", params, callback);
        int result = (Integer) callback.result();
        if(result != -1){
            AVMPlayerManager.getManager().addReset(avmPlayer, result);
            player.sendMessage("§aYou have a reset available. Use §6/reset §ato reset your team and kit.");
            player.sendMessage("§c§lWARNING: §aThe reset is irreversible. Your inventory and homes will be cleared and you will be removed from your faction!");
        }
    }

    @Override
    public Object result() {
        return null;
    }
}
