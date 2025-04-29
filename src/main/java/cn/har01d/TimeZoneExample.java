package cn.har01d;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class TimeZoneExample {
    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime now = ZonedDateTime.now(zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV");
        String dateTime = now.format(formatter);
        System.out.println("Current time in China Standard Time: " + now.format(formatter));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        System.out.println(ZonedDateTime.parse(dateTime, formatter));
        System.out.println("Current time in China Standard Time: " + now.format(formatter));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzzz");
        System.out.println("Current time in China Standard Time: " + now.format(formatter));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println("Current time in China Standard Time: " + now.format(formatter));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZZZ");
        System.out.println("Current time in China Standard Time: " + now.format(formatter));
        /*
        Time Zone
            z: Time zone name (e.g., zzz for PST, zzzz for Pacific Standard Time)
            Z: Offset from UTC (e.g., Z for -0800)
            X: ISO 8601 time zone (e.g., X for -08, XX for -0800, XXX for -08:00)
            V: Time zone ID (e.g., VV for America/Los_Angeles)
         */

        formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy");
        System.out.println(formatter.format(now));

        formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        System.out.println(formatter.format(Instant.now().atZone(ZoneId.of("Z"))));

        System.out.println(ZoneId.of("PST", ZoneId.SHORT_IDS));
        System.out.println(ZoneId.of("CST", ZoneId.SHORT_IDS));
    }
}
