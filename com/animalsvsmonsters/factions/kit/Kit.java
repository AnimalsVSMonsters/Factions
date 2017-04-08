package com.animalsvsmonsters.factions.kit;

import com.animalsvsmonsters.factions.team.Team;

import me.libraryaddict.disguise.disguisetypes.Disguise;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 1/31/2016 at 10:00 PM.
 * ******************************************************************************************
 */
public abstract class Kit {

    public abstract String getName();
    public abstract Team getTeam();
    public abstract int getKitNumber();
    public abstract Disguise getDisguise();
}
