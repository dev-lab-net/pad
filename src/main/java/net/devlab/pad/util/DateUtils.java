package net.devlab.pad.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * 
 * @author dj0n1
 *
 */
public class DateUtils {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateUtils() {
    }

    public static String format(Temporal temporal) {
        return dateFormatter.format(temporal);
    }

    public static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, dateFormatter);
    }
}
