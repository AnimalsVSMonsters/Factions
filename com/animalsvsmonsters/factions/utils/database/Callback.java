package com.animalsvsmonsters.factions.utils.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Callback 
{
	
    /**
     * Read data from the resultset, should not do any data processing
     * @param rs
     * @throws java.sql.SQLException
     */
    public void read(ResultSet rs) throws SQLException;
    
    /**
     * for any Bukkit API work
     */
    public void digestSync();
    
    
    /**
     * Should only be called on Sync Queries
     * @return
     */
    public Object result();
}
