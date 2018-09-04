package com.btasdemir.loganalyzer.util;

import com.btasdemir.loganalyzer.model.Duration;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LogAnalyzerDateUtilsTest {

    @Test
    public void it_should_get_end_date_according_to_daily_duration() throws ParseException {
        //Given
        String startDate = "2018-01-01.00:00:00";

        //When
        Date endDate = LogAnalyzerDateUtils
                .getEndDateAccordingTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue(startDate), Duration.DAILY);

        //Then
        assertThat(endDate).isEqualTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2018-01-02.00:00:00"));
    }

    @Test
    public void it_should_get_end_date_as_start_date_according_to_default_duration() throws ParseException {
        //Given
        String startDate = "2018-01-01.00:00:00";

        //When
        Date endDate = LogAnalyzerDateUtils
                .getEndDateAccordingTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue(startDate), Duration.DEFAULT);

        //Then
        assertThat(endDate).isEqualTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2018-01-01.00:00:00"));
    }
}