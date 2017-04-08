package com.animalsvsmonsters.factions.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/1/2016 at 12:22 AM.
 * ******************************************************************************************
 */
public class LocationUtil {

    public static String serializeLocation(Location l){
        String s = "";
        s += "@W:" + l.getWorld().getName();
        s += ";@X:" + l.getX();
        s += ";@Y:" + l.getY();
        s += ";@Z:" + l.getZ();
        s += ";@P:" + l.getPitch();
        s += ";@YA:" + l.getYaw();

        return s;
    }

    public static Location deserializeLocation(String s) {
        Location l = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
        String[] att = s.split(";");
        for(String attribute : att) {
            String[] split = attribute.split(":");
            if(split[0].equalsIgnoreCase("@W"))
                l.setWorld(Bukkit.getWorld(split[1]));
            if(split[0].equalsIgnoreCase("@X"))
                l.setX(Double.parseDouble(split[1]));
            if(split[0].equalsIgnoreCase("@Y"))
                l.setY(Double.parseDouble(split[1]));
            if(split[0].equalsIgnoreCase("@Z"))
                l.setZ(Double.parseDouble(split[1]));
            if(split[0].equalsIgnoreCase("@P"))
                l.setPitch(Float.parseFloat(split[1]));
            if(split[0].equalsIgnoreCase("@YA"))
                l.setYaw(Float.parseFloat(split[1]));

        }
        return l;
    }

}
