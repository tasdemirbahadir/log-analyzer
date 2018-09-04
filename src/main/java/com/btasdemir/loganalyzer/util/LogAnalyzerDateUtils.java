package com.btasdemir.loganalyzer.util;

import com.btasdemir.loganalyzer.model.Duration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class LogAnalyzerDateUtils {

    private LogAnalyzerDateUtils() {
        throw new AssertionError();
    }

    public static Date parseCommandLineArgumentDateValue(String dateValue) throws ParseException {
        final SimpleDateFormat cliDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
        return cliDateFormat.parse(dateValue);
    }

    public static Date parseLogFileDateValue(String dateValue) throws ParseException {
        final SimpleDateFormat logFileDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return logFileDateFormat.parse(dateValue);
    }

    public static Date getEndDateAccordingTo(Date startDate, Duration duration) {
        if (duration.equals(Duration.HOURLY)) {
            return getDateFrom(convertToLocaleDateTimeFrom(startDate).plusHours(1));
        } else if (duration.equals(Duration.DAILY)) {
            return getDateFrom(convertToLocaleDateTimeFrom(startDate).plusDays(1));
        } else {
            return startDate;
        }
    }

    private static LocalDateTime convertToLocaleDateTimeFrom(Date date) {
        return LocalDateTime.from(date.toInstant().atZone(ZoneId.of("UTC")));
    }

    private static Date getDateFrom(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return Date.from(instant);
    }

    public static boolean isDateEqualsOrAfter(Date value, Date reference) {
        return value.equals(reference) || value.after(reference);
    }

    public static boolean isDateEqualsOrBefore(Date value, Date reference) {
        return value.equals(reference) || value.before(reference);
    }

}
