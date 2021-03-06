package com.animalsvsmonsters.factions.callbacks;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.utils.database.Callback;
import com.animalsvsmonsters.factions.utils.database.Database;

public class TeamInfoCallback implements Callback {

    public String teamName;

    private Team team;
    private Integer kills;
    private Integer deaths;

    public TeamInfoCallback(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(rs.next()){
            this.kills = rs.getInt("kills");
            this.deaths = rs.getInt("deaths");
            team = new Team(teamName, kills, deaths);
        }
    }

    @Override
    public void digestSync() {
        if(kills == null && deaths == null){
            Object[] params = new Object[]{teamName, 0, 0};
            Database.get().syncUpdate("INSERT INTO team_info (team, kills, deaths) VALUES (?, ?, ?)", params);
            params = new Object[]{teamName};
            Database.get().syncQuery("SELECT * FROM team_info WHERE team = ?", params, this);
            team = new Team(teamName, 0, 0);
        }
    }

    @Override
    public Object result() {
        return team;
    }
}
