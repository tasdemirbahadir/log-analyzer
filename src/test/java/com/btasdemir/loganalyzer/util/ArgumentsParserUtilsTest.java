package com.btasdemir.loganalyzer.util;

import com.btasdemir.loganalyzer.resources.OptionsResources;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentsParserUtilsTest {

    @Test
    public void it_should_parse_arguments_to_options_resources_properly() throws ParseException, java.text.ParseException {
        //Given
        ArgumentsParserUtils argumentsParserUtils = new ArgumentsParserUtils();
        String[] args = new String[]{"--accesslog=/path/to/file", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};

        //When
        argumentsParserUtils.parse(args);

        //Then
        assertThat(OptionsResources.getAccessLog()).isEqualTo("/path/to/file");
        assertThat(OptionsResources.getStartDate()).isEqualTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.13:00:00"));
        assertThat(OptionsResources.getThreshold()).isEqualTo(100);
    }
}