package com.animalsvsmonsters.factions.kit;

import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.team.TeamManager;

import me.libraryaddict.disguise.disguisetypes.Disguise;

public class Default extends Kit {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Team getTeam() {
        return TeamManager.getManager().getTeam("Limbo");
    }

    @Override
    public int getKitNumber() {
        return 0;
    }

    @Override
    public Disguise getDisguise() {
        return null;
    }
}
