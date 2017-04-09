package com.animalsvsmonsters.factions.kit.AnimalKits;

import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.team.TeamManager;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class Pig extends Kit {

    @Override
    public String getName() {
        return "Pig";
    }

    @Override
    public Team getTeam() {
        return TeamManager.getManager().getTeam("Animals");
    }

    @Override
    public int getKitNumber() {
        return 1;
    }

    @Override
    public Disguise getDisguise() {
        return new MobDisguise(DisguiseType.PIG);
    }
}
