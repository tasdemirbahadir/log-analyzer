package com.btasdemir.loganalyzer.resources;

import java.util.Date;

/**
 * Bahadir Tasdemir - bahadir.tasdemir@hotmail.com.tr
 * <p>
 * This class is used for getting CLI options
 * from args to Spring Context.
 * <p>
 * The values are assigned to static variables
 * and only used by the class 'ArgumentsParserUtils'
 * to assign values and by the class 'LogAnalyzerService'
 * to pass values into a dto.
 * <p>
 * Static variables must be used cautiously so that
 * this values are assigned to a dto and passed with
 * it to the places where the values are needed except
 * the classes mentioned above.
 */
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
