package com.animalsvsmonsters.factions.callbacks;

import org.bukkit.entity.Player;

import com.animalsvsmonsters.factions.utils.Database.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

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
