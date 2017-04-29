package com.animalsvsmonsters.factions.team;

import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.callbacks.TeamInfoCallback;
import com.animalsvsmonsters.factions.utils.database.Database;

import java.util.HashMap;

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
