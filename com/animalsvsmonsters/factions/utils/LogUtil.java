package com.animalsvsmonsters.factions.utils;

import com.animalsvsmonsters.factions.SystemColor;

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
