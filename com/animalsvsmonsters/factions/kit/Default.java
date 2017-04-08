package com.animalsvsmonsters.factions.kit;

import com.animalsvsmonsters.factions.team.Team;
import com.animalsvsmonsters.factions.team.TeamManager;

import me.libraryaddict.disguise.disguisetypes.Disguise;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 1/31/2016 at 11:27 PM.
 * ******************************************************************************************
 */
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
