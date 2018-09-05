package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.model.dto.OptionsResourcesDto;
import com.btasdemir.loganalyzer.resources.OptionsResources;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OptionsResourcesServiceTest {

    @InjectMocks
    private OptionsResourcesService optionsResourcesService;

    @Test
    public void it_should_get_access_log_file_path() {
        //Given
        OptionsResources.setAccessLog("/path/to/file");

        //When
        String givenAccessLogFilePath = optionsResourcesService.getAccessLog();

        //Then
        assertThat(givenAccessLogFilePath).isEqualTo("/path/to/file");
    }

    @Test
    public void it_should_get_options_resources_dto_properly() throws ParseException {
        //Given
        OptionsResources.setStartDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2018-01-01.01:00:00"));
        OptionsResources.setEndDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2018-01-02.01:00:00"));
        OptionsResources.setThreshold(1);

        //When
        OptionsResourcesDto optionsResourcesDto = optionsResourcesService.getOptionsResourcesDto();

        //Then
        assertThat(optionsResourcesDto.getStartDate()).isEqualTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2018-01-01.01:00:00"));
        assertThat(optionsResourcesDto.getEndDate()).isEqualTo(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2018-01-02.01:00:00"));
        assertThat(optionsResourcesDto.getThreshold()).isEqualTo(1);
    }
}