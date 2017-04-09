package com.animalsvsmonsters.factions.kit.MonsterKits;

import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.team.TeamManager;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class Enderman extends Kit {

    @Override
    public String getName() {
        return "Enderman";
    }

    @Override
    public Team getTeam() {
        return TeamManager.getManager().getTeam("Monsters");
    }

    @Override
    public int getKitNumber() {
        return 1;
    }

    @Override
    public Disguise getDisguise() {
        return new MobDisguise(DisguiseType.ENDERMAN);
    }
}
