package com.animalsvsmonsters.factions.callbacks;

import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.utils.Database.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/1/2016 at 4:14 PM.
 * ******************************************************************************************
 */
public class ResetQueueCallback implements Callback {

    public UUID uuid;

    private int resetQueue = -1;

    public ResetQueueCallback(Player player) {
        this.uuid = player.getUniqueId();
    }

    public ResetQueueCallback(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(rs.next()){
            resetQueue = rs.getInt("resetkey");
        }
    }

    @Override
    public void digestSync() {

    }

    @Override
    public Object result() {
        return resetQueue;
    }
}
