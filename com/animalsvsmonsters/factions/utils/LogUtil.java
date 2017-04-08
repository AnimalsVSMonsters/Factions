package com.animalsvsmonsters.factions.utils;

import com.animalsvsmonsters.factions.SystemColor;

/**
 * * **************************************************************************************
 * Copyright MikeTheDev (c) 2016.  All Rights Reserved.
 * Any code contained within AVMFactions (this document), and any associated APIs with similar branding
 * are the sole property of Michael Petramalo.  Distribution, reproduction, taking sections, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with the third-party, you.
 * Thanks.
 * Created on 1/31/2016 at 8:53 PM.
 * ******************************************************************************************
 */
public class LogUtil {

    public static void log(LogType type, String log){
        System.out.println(type.getPrint() + type.getColor() + log + SystemColor.RESET);
    }

    public enum LogType{
        INFO(SystemColor.YELLOW, SystemColor.LIME + "[INFO]   " + SystemColor.RESET),
        SEVERE(SystemColor.RED, SystemColor.MAROON + "[SEVERE]   " + SystemColor.RESET),
        DEBUG(SystemColor.CYAN, SystemColor.AQUAMARINE + "[DEBUG]   " + SystemColor.RESET),
        WARN(SystemColor.GOLD, SystemColor.YELLOW + "[WARN]   " + SystemColor.RESET);

        private SystemColor color;
        private String print;

        LogType(SystemColor systemColor, String print){
            color = systemColor;
            this.print = print;
        }

        public SystemColor getColor() {
            return color;
        }

        public String getPrint() {
            return print;
        }
    }

}
