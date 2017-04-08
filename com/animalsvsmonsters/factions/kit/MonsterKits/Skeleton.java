package com.animalsvsmonsters.factions.kit.MonsterKits;

import com.animalsvsmonsters.factions.kit.Kit;
import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.team.TeamManager;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 1/31/2016 at 10:02 PM.
 * ******************************************************************************************
 */
public class Skeleton extends Kit {

    @Override
    public String getName() {
        return "Skeleton";
    }

    @Override
    public Team getTeam() {
        return TeamManager.getManager().getTeam("Monsters");
    }

    @Override
    public int getKitNumber() {
        return 2;
    }

    @Override
    public Disguise getDisguise() {
        return new MobDisguise(DisguiseType.SKELETON);
    }
}
