package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.domain.Log;
import com.btasdemir.loganalyzer.model.LogEntry;
import com.btasdemir.loganalyzer.model.dto.OptionsResourcesDto;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogAnalyzerServiceTest {

    @InjectMocks
    private LogAnalyzerService logAnalyzerService;

    @Mock
    private LogFileParserService logFileParserService;

    @Mock
    private LogFiltererService logFiltererService;

    @Mock
    private LogSaveService logSaveService;

    @Mock
    private LogHelper logHelper;

    @Mock
    private OptionsResourcesService optionsResourcesService;

    @Captor
    private ArgumentCaptor<List<LogEntry>> logEntriesArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<String>> blockedIpsArgumentCaptor;

    @Test
    public void it_should_filter_ips_print_blocked_ones_to_console_and_save_all_logs_into_database() throws IOException, ParseException {
        //Given
        ArgumentCaptor<String> logFilePathStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        //get access log file path
        given(optionsResourcesService.getAccessLog()).willReturn("/log/file/path");

        //log file parser service preparation
        LogEntry logEntry1 = new LogEntry();
        LogEntry logEntry2 = new LogEntry();
        List<LogEntry> logEntries = Arrays.asList(logEntry1, logEntry2);
        given(logFileParserService.parseLogFileLocatedAt(anyString())).willReturn(logEntries);

        //get options resources dto
        OptionsResourcesDto optionsResourcesDto = new OptionsResourcesDto();
        optionsResourcesDto.setStartDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.00:00:00.00"));
        optionsResourcesDto.setEndDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.00:00:00.00"));
        optionsResourcesDto.setThreshold(1);
        given(optionsResourcesService.getOptionsResourcesDto()).willReturn(optionsResourcesDto);

        //log filterer service preparation
        ArgumentCaptor<OptionsResourcesDto> optionsResourcesDtoArgumentCaptor = ArgumentCaptor
                .forClass(OptionsResourcesDto.class);
        List<String> blockedIps = Arrays.asList("1.1.1.1", "2.2.2.2");
        given(logFiltererService.filterBlockedIpsAndPrintToConsole(any(), any(OptionsResourcesDto.class)))
                .willReturn(blockedIps);

        //log helper preparation
        List<Log> logs = new ArrayList<>();
        given(logHelper.convertLogEntriesToLogsAndSetCauseToBlock(any(), any(),
                optionsResourcesDtoArgumentCaptor.capture())).willReturn(logs);

        //When
        logAnalyzerService.analyzeLogs();

        //Then
        //verifying object for options resources dto
        OptionsResourcesDto verifyingOptionsResourcesDto = new OptionsResourcesDto();
        verifyingOptionsResourcesDto.setThreshold(1);
        verifyingOptionsResourcesDto.setStartDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.00:00:00.00"));
        verifyingOptionsResourcesDto.setEndDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.00:00:00.00"));

        //verify log file parser service
        verify(logFileParserService, times(1))
                .parseLogFileLocatedAt(logFilePathStringArgumentCaptor.capture());
        String logFilePath = logFilePathStringArgumentCaptor.getValue();
        assertThat(logFilePath).isEqualTo("/log/file/path");

        //verify log filterer service
        verify(logFiltererService, times(1)).filterBlockedIpsAndPrintToConsole(logEntriesArgumentCaptor.capture(),
                optionsResourcesDtoArgumentCaptor.capture());
        List<LogEntry> capturedLogEntries = logEntriesArgumentCaptor.getValue();
        assertThat(capturedLogEntries).isEqualTo(logEntries);
        OptionsResourcesDto capturedOptionsResourcesDto = optionsResourcesDtoArgumentCaptor.getValue();
        assertThat(capturedOptionsResourcesDto).isEqualToComparingFieldByField(verifyingOptionsResourcesDto);

        //verify log helper
        verify(logHelper, times(1)).convertLogEntriesToLogsAndSetCauseToBlock(logEntriesArgumentCaptor.capture(),
                blockedIpsArgumentCaptor.capture(), optionsResourcesDtoArgumentCaptor.capture());
        List<LogEntry> capturedLogEntriesOfLogHelper = logEntriesArgumentCaptor.getValue();
        assertThat(capturedLogEntriesOfLogHelper).isEqualTo(logEntries);
        List<String> capturedBlockedIps = blockedIpsArgumentCaptor.getValue();
        assertThat(capturedBlockedIps).isEqualTo(blockedIps);

        //verify log save service
        verify(logSaveService, times(1)).saveLogs(logs);
    }
}