package com.animalsvsmonsters.factions.utils;

public class TimeParse {

    public static String parse(long milliseconds) {
        return parseTime(milliseconds) + parseString(milliseconds);
    }

    private static double parseTime(long milliseconds) {
        if (milliseconds < 1000L) {
            double seconds = milliseconds / 1000.0D;
            seconds = round(seconds, 3);
            return seconds;
        }
        if ((milliseconds >= 1000L) && (milliseconds < 60000L)) {
            double seconds = milliseconds / 1000.0D;
            seconds = round(seconds, 2);
            return seconds;
        }
        double minutes = milliseconds / 60000.0D;
        minutes = round(minutes, 1);
        return minutes;
    }

    private static String parseString(long milliseconds) {
        if ((milliseconds >= 1000L) && (milliseconds < 60000L)) {
            return " Seconds";
        }
        if (milliseconds >= 60000L) {
            return " Minutes";
        }
        return " Seconds";
    }

    private static double round(double value, int placevalues) {
        if (placevalues < 0) {
            throw new IllegalArgumentException();
        }
        long factor = (long) Math.pow(10.0D, placevalues);
        value *= factor;
        long tmp = Math.round(value);
        return tmp / factor;
    }

}
