package cn.har01d;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class PeriodExample {
    public static void main(String[] args) {
        System.out.println(secondsToPeriod(30));
        System.out.println(secondsToPeriod(600));
        System.out.println(secondsToPeriod(606));
        System.out.println(secondsToPeriod(3600));
        System.out.println(secondsToPeriod(3600 + 2));
        System.out.println(secondsToPeriod(3600 + 120 + 3));
        System.out.println(secondsToPeriod(24 * 3600));
        System.out.println(secondsToPeriod(24 * 3600 + 2));
        System.out.println(secondsToPeriod(24 * 3600 + 120));
        System.out.println(secondsToPeriod(24 * 3600 + 123));
        System.out.println(secondsToPeriod(24 * 3600 + 3600));
        System.out.println(secondsToPeriod(24 * 3600 + 3601));
        System.out.println(secondsToPeriod(24 * 3600 + 3600 + 123));
        System.out.println(secondsToPeriod(7 * 24 * 3600));
        System.out.println(secondsToPeriod(30 * 24 * 3600));
        System.out.println(secondsToPeriod(32 * 24 * 3600 + 3600 + 123));
        System.out.println(secondsToPeriod(365 * 24 * 3600 + 3600 + 123));
        System.out.println(secondsToPeriod(366 * 24 * 3600 + 3600 + 123));
        System.out.println(secondsToPeriod(1024 * 24 * 3600 + 5));

        Instant start = Instant.now().truncatedTo(ChronoUnit.MINUTES);
        Instant end = start.minus(150, ChronoUnit.MINUTES);
        Duration diff = Duration.between(start, end);
        System.out.println(diff);
        System.out.println(diff.toHoursPart());
        System.out.println(diff.toMinutesPart());
        System.out.println(ZoneOffset.ofHoursMinutes(8, 0));
        System.out.println(ZoneOffset.ofHoursMinutes(-2, -30));
        System.out.println(ZoneOffset.ofTotalSeconds(149 * 60));
        System.out.println(ZoneOffset.ofTotalSeconds(151 * 60));
        System.out.println();
        System.out.println(toZoneOffset(149 * 60));
        System.out.println(toZoneOffset(150 * 60));
        System.out.println(toZoneOffset(151 * 60));
        System.out.println();
        System.out.println(toZoneOffset(-149 * 60));
        System.out.println(toZoneOffset(-150 * 60));
        System.out.println(toZoneOffset(-151 * 60));

//        System.out.println(Period.of(0, 3, 5));
//        System.out.println(Period.of(1, 0, 1));
//        System.out.println(Period.of(5, 2, 3));
        System.out.println(Duration.parse("P2D"));
        System.out.println(Duration.parse("P2M"));  // DateTimeParseException
        System.out.println(Duration.parse("P2Y"));  // DateTimeParseException
    }

    private static ZoneOffset toZoneOffset(long seconds) {
        long minutes = seconds / 60;
        minutes = Math.round(minutes / 15.0) * 15;
        seconds = minutes * 60;

        return ZoneOffset.ofTotalSeconds((int) seconds);
    }

    private static String secondsToPeriod(long totalSeconds) {
        long days = totalSeconds / (24 * 3600);
        long remainingSeconds = totalSeconds % (24 * 3600);

        String result = "";
        if (days > 0) {
            long years = days / 365;
            days -= years * 365;
            long months = days / 30;
            days -= months * 30;
            result += Period.of((int) years, (int) months, (int) days).toString();
        }
        if (remainingSeconds > 0) {
            String duration = Duration.ofSeconds(remainingSeconds).toString();
            if (days > 0) {
                duration = duration.substring(1);
            }
            result += duration;
        }
        return result;
    }
}
