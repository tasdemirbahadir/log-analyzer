package com.btasdemir.loganalyzer;

import com.btasdemir.loganalyzer.resources.OptionsResources;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogAnalyzerApplicationTest {

    @Test
    public void it_should_set_options_resources_from_given_args_when_main_method_is_run() throws ParseException,
            java.text.ParseException {
        //Given
        String[] args = new String[]{"--accesslog=access.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly",
                "--threshold=100"};

        //When
        LogAnalyzerApplication.main(args);

        //Then
        assertThat(OptionsResources.getAccessLog()).isEqualTo("access.log");
        assertThat(OptionsResources.getThreshold()).isEqualTo(100);
        assertThat(OptionsResources.getStartDate())
                .isEqualTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.13:00:00"));
        assertThat(OptionsResources.getEndDate())
                .isEqualTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.14:00:00"));
    }
}