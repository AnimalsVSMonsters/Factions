package com.animalsvsmonsters.factions.team;

import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.callbacks.TeamInfoCallback;
import com.animalsvsmonsters.factions.utils.Database.Database;

import java.util.HashMap;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 1/31/2016 at 10:27 PM.
 * ******************************************************************************************
 */
public class TeamManager {

    private HashMap<String, Team> teams = new HashMap<String, Team>();

    public Team getTeam(String teamName){
        for(Team team : teams.values()){
            if(team.getTeamName().equalsIgnoreCase(teamName))
                return team;
        }
        return null;
    }

    public Team getTeam(Player player){
        for(Team team : teams.values()){
            if(team.hasMember(player))
                return team;
        }
        return null;
    }

    private void createTeam(String teamName){
        if(!teamName.equalsIgnoreCase("Limbo")) {
            TeamInfoCallback callback = new TeamInfoCallback(teamName);
            Object[] params = new Object[]{teamName};
            Database.get().syncQuery("SELECT * FROM team_info WHERE team = ?", params, callback);
            Team team = (Team) callback.result();
            teams.put(teamName, team);
            return;
        }
        teams.put(teamName, new Team(teamName, -1, -1));
    }

    public void registerTeams(){
        createTeam("Limbo");
        createTeam("Animals");
        createTeam("Monsters");
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }

    private static TeamManager manager = new TeamManager();

    public static TeamManager getManager() {
        return manager;
    }
}
