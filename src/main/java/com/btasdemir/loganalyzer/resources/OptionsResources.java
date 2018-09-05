package com.btasdemir.loganalyzer.resources;

import java.util.Date;

public class OptionsResources {

    private OptionsResources() {
        throw new AssertionError();
    }

    private static String accessLog;
    private static Date startDate;
    private static Date endDate;
    private static Integer threshold;

    public static String getAccessLog() {
        return accessLog;
    }

    public static void setAccessLog(String accessLog) {
        OptionsResources.accessLog = accessLog;
    }

    public static Date getEndDate() {
        return endDate;
    }

    public static void setEndDate(Date endDate) {
        OptionsResources.endDate = endDate;
    }

    public static Date getStartDate() {
        return startDate;
    }

    public static void setStartDate(Date startDate) {
        OptionsResources.startDate = startDate;
    }

    public static Integer getThreshold() {
        return threshold;
    }

    public static void setThreshold(Integer threshold) {
        OptionsResources.threshold = threshold;
    }

}
