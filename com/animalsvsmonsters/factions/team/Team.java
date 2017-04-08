package com.animalsvsmonsters.factions.team;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.Main;
import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.storage.AVMPlayer;
import com.animalsvsmonsters.factions.storage.AVMPlayerManager;
import com.animalsvsmonsters.factions.utils.LocationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 1/31/2016 at 9:58 PM.
 * ******************************************************************************************
 */
public class Team {

    private String teamName;
    private List<AVMPlayer> members;
    private List<Kit> kits;
    private int kills;
    private int deaths;
    private Location spawn;

    public Team(String teamName, int kills, int deaths) {
        this.teamName = teamName;
        this.members = new ArrayList<AVMPlayer>();
        this.kits = new ArrayList<Kit>();
        this.kills = kills;
        this.deaths = deaths;
        this.spawn = LocationUtil.deserializeLocation(Main.getInstance().getSpawns().getString(teamName));
    }

    public String getTeamName() {
        return teamName;
    }

    public List<AVMPlayer> getMembers() {
        return members;
    }

    public void addMember(AVMPlayer player){
        members.add(player);
    }

    public void removeMember(AVMPlayer player){
        if(members.contains(player))
            members.remove(player);
    }

    public boolean hasMember(Player player){
        return members.contains(AVMPlayerManager.getManager().getPlayer(player));
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public List<Kit> getKits() {
        return kits;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void addKill(){
        kills += 1;
    }

    public void addDeath(){
        deaths += 1;
    }

}
