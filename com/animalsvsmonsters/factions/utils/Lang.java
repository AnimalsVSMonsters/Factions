package com.animalsvsmonsters.factions.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 2/1/2016 at 12:11 AM.
 * ******************************************************************************************
 */

public enum Lang {

    PREFIX("prefix", "&4[&cAVM Factions&4]  "),
    CONSOLE_SENDER("console", "&cYou cannot perform this command from console!"),
    MANY_ARGS("many_args", "&cToo many arguments. Please use &6/help &cfor more information"),
    NO_PERMS("perms", "&cYou do not have permission to perform this command. If you feel this is a mistake, please contact a server administrator."),
    SET_LIMBO("limbo_spawn", "&aYou successfully set the &2Limbo &aspawn location to where you are standing."),
    SET_ANIMAL("animal_spawn", "&aYou successfully set the &2Animal &aspawn location to where you are standing."),
    SET_MONSTER("monster_spawn", "&aYou successfully set the &2Monster &aspawn location to where you are standing."),
    TELEPORT("spawn_tp", "&aYou have been teleported back to your spawn"),
    TEAM_DENY("f_team", "&cYou cannot invite somebody to join your faction that is on a different team!"),
    NO_RESET("no_reset", "&cYou do not have any class resets available. You can purchase one at http://www.avmfactions.com");

    private final String path;
    private final String def;
    private static YamlConfiguration LANG;

    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        if (this == PREFIX) {
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        }
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }

}
