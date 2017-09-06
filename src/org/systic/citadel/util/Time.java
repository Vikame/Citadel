package org.systic.citadel.util;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class Time {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.0");
    private boolean incWeeks, incDays, incHours, incMinutes, showDecimal, useFullNames, useColons;

    public static String format(long millis) {
        return FORMAT.format(((double) millis / 1000.0));
    }

    public Time includeWeeks(boolean val) {
        this.incWeeks = val;
        return this;
    }

    public Time includeDays(boolean val) {
        this.incDays = val;
        return this;
    }

    public Time includeHours(boolean val) {
        this.incHours = val;
        return this;
    }

    public Time includeMinutes(boolean val) {
        this.incMinutes = val;
        return this;
    }

    public Time showDecimal(boolean val) {
        this.showDecimal = val;
        return this;
    }

    public Time useFullNames(boolean val) {
        this.useFullNames = val;
        if (val) this.useColons = false;
        return this;
    }

    public Time useColons(boolean val) {
        this.useColons = val;
        if (val) this.useFullNames = false;
        return this;
    }

    public String getFormatted(long millis) {
        boolean negative = (millis < 0);
        millis = Math.abs(millis);

        int weeks = 0, days = 0, hours = 0, minutes = 0;

        if (incWeeks) {
            while (millis >= 604800000) {
                millis -= 604800000;
                weeks++;
            }
        }
        if (incDays) {
            while (millis >= 86400000) {
                millis -= 86400000;
                days++;
            }
        }
        if (incHours) {
            while (millis >= 3600000) {
                millis -= 3600000;
                hours++;
            }
        }
        if (incMinutes) {
            while (millis >= 60000) {
                millis -= 60000;
                minutes++;
            }
        }

        int sec = 0;
        String seconds;
        if (showDecimal) {
            double val = ((double) millis / 1000.0);
            sec = (int) Math.floor(val);
            seconds = FORMAT.format(val);
        } else {
            sec = (int) (millis / 1000);
            seconds = "" + sec;
        }

        String ret = "";
        if (weeks > 0) {
            String add = "" + weeks;

            if (this.useFullNames) {
                add += " week" + (weeks != 1 ? "s" : "") + " ";
            } else add += "w ";

            ret += add;
        }

        if (days > 0) {
            String add = "" + days;

            if (days < 10 && days > -10) add = "0" + add;

            if (this.useFullNames) {
                add += " day" + (days != 1 ? "s" : "") + " ";
            } else add += "d ";

            ret += add;
        }

        if (hours > 0) {
            String add = "" + hours;

            if (hours < 10 && hours > -10) add = "0" + add;

            if (this.useColons) {
                add += ":";
            } else {
                if (this.useFullNames) {
                    add += " hour" + (hours != 1 ? "s" : "") + " ";
                } else add += "h ";
            }

            ret += add;
        }

        if (minutes > 0) {
            String add = "" + minutes;

            if (minutes < 10 && minutes > -10) add = "0" + add;

            if (this.useColons) {
                add += ":";
            } else {
                if (this.useFullNames) {
                    add += " minute" + (minutes != 1 ? "s" : "") + " ";
                } else add += "m ";
            }

            ret += add;
        }

        return ((negative ? "-" : "") + ret + (sec < 10 && sec > -10 ? "0" : "") + seconds + "s").trim();
    }

    public static long parse(String input) {
        try {
            if ((input == null) || (input.isEmpty())) {
                return -1;
            }
            long result = 0L;
            StringBuilder number = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (Character.isDigit(c)) {
                    number.append(c);
                } else {
                    String str;
                    if ((Character.isLetter(c)) && (!(str = number.toString()).isEmpty())) {
                        result += convert(Integer.parseInt(str), c);
                        number = new StringBuilder();
                    }
                }
            }
            return result;
        }catch(Exception ignored){}
        return 0;
    }

    public static long convert(int value, char unit) {
        switch (unit) {
            case 'y':
                return value * TimeUnit.DAYS.toMillis(365L);
            case 'M':
                return value * TimeUnit.DAYS.toMillis(30L);
            case 'w':
                return value * TimeUnit.DAYS.toMillis(7L);
            case 'd':
                return value * TimeUnit.DAYS.toMillis(1L);
            case 'h':
                return value * TimeUnit.HOURS.toMillis(1L);
            case 'm':
                return value * TimeUnit.MINUTES.toMillis(1L);
            default:
                return value * TimeUnit.SECONDS.toMillis(1L);
        }
    }

}
