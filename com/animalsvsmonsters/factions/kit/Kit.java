package com.animalsvsmonsters.factions.kit;

import com.animalsvsmonsters.factions.team.Team;

import me.libraryaddict.disguise.disguisetypes.Disguise;

public abstract class Kit {

    public abstract String getName();
    public abstract Team getTeam();
    public abstract int getKitNumber();
    public abstract Disguise getDisguise();
}
