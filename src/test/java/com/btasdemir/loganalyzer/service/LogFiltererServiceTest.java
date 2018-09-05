package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.model.LogEntry;
import com.btasdemir.loganalyzer.model.dto.OptionsResourcesDto;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.rule.OutputCapture;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class LogFiltererServiceTest {

    @InjectMocks
    private LogFiltererService logFiltererService;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void it_should_filter_blocked_ips_and_print_to_console_with_duration_of_hourly() throws ParseException {
        //Given
        LogEntry logEntry1 = new LogEntry();
        logEntry1.setIp("1.1.1.1");
        logEntry1.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 00:00:00.000"));

        LogEntry logEntry2 = new LogEntry();
        logEntry2.setIp("2.2.2.2");
        logEntry2.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 01:00:00.000"));

        LogEntry logEntry22 = new LogEntry();
        logEntry22.setIp("2.2.2.2");
        logEntry22.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 01:01:00.000"));

        LogEntry logEntry23 = new LogEntry();
        logEntry23.setIp("2.2.2.2");
        logEntry23.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 01:02:00.000"));

        LogEntry logEntry3 = new LogEntry();
        logEntry3.setIp("3.3.3.3");
        logEntry3.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 01:59:59.999"));

        LogEntry logEntry32 = new LogEntry();
        logEntry32.setIp("3.3.3.3");
        logEntry32.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 01:59:00.000"));

        LogEntry logEntry33 = new LogEntry();
        logEntry33.setIp("3.3.3.3");
        logEntry33.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 01:58:00.000"));

        LogEntry logEntry4 = new LogEntry();
        logEntry4.setIp("4.4.4.4");
        logEntry4.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 03:00:00.000"));

        List<LogEntry> logEntryList = Arrays.asList(logEntry1, logEntry2, logEntry22, logEntry23, logEntry3, logEntry32,
                logEntry33, logEntry4);

        OptionsResourcesDto optionsResourcesDto = new OptionsResourcesDto();
        optionsResourcesDto.setStartDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.01:00:00.00"));
        optionsResourcesDto.setEndDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.02:00:00.00"));
        optionsResourcesDto.setThreshold(3);

        //When
        List<String> blockedIpList = logFiltererService.filterBlockedIpsAndPrintToConsole(logEntryList, optionsResourcesDto);

        //Then
        assertThat(blockedIpList).hasSize(2);
        assertThat(blockedIpList).contains("2.2.2.2", "3.3.3.3");

        outputCapture.expect(containsString("2.2.2.2"));
        outputCapture.expect(containsString("3.3.3.3"));
    }

    @Test
    public void it_should_filter_blocked_ips_and_print_to_console_with_duration_of_daily() throws ParseException {
        //Given
        LogEntry logEntry1 = new LogEntry();
        logEntry1.setIp("1.1.1.1");
        logEntry1.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 00:00:00.000"));

        LogEntry logEntry2 = new LogEntry();
        logEntry2.setIp("2.2.2.2");
        logEntry2.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-02 01:00:00.000"));

        LogEntry logEntry22 = new LogEntry();
        logEntry22.setIp("2.2.2.2");
        logEntry22.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-02 01:01:00.000"));

        LogEntry logEntry23 = new LogEntry();
        logEntry23.setIp("2.2.2.2");
        logEntry23.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-02 01:02:00.000"));

        LogEntry logEntry3 = new LogEntry();
        logEntry3.setIp("3.3.3.3");
        logEntry3.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-03 00:59:59.999"));

        LogEntry logEntry32 = new LogEntry();
        logEntry32.setIp("3.3.3.3");
        logEntry32.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-03 00:59:00.000"));

        LogEntry logEntry33 = new LogEntry();
        logEntry33.setIp("3.3.3.3");
        logEntry33.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-03 00:58:00.000"));

        LogEntry logEntry4 = new LogEntry();
        logEntry4.setIp("4.4.4.4");
        logEntry4.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-03 03:00:00.000"));

        List<LogEntry> logEntryList = Arrays.asList(logEntry1, logEntry2, logEntry22, logEntry23, logEntry3, logEntry32,
                logEntry33, logEntry4);

        OptionsResourcesDto optionsResourcesDto = new OptionsResourcesDto();
        optionsResourcesDto.setStartDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-02.01:00:00.00"));
        optionsResourcesDto.setEndDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-03.01:00:00.00"));
        optionsResourcesDto.setThreshold(3);

        //When
        List<String> blockedIpList = logFiltererService.filterBlockedIpsAndPrintToConsole(logEntryList, optionsResourcesDto);

        //Then
        assertThat(blockedIpList).hasSize(2);
        assertThat(blockedIpList).contains("2.2.2.2", "3.3.3.3");

        outputCapture.expect(containsString("2.2.2.2"));
        outputCapture.expect(containsString("3.3.3.3"));
    }
}