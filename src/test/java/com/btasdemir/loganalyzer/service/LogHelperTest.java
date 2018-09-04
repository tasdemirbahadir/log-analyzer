package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.domain.Log;
import com.btasdemir.loganalyzer.model.LogEntry;
import com.btasdemir.loganalyzer.model.dto.OptionsResourcesDto;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LogHelperTest {

    @InjectMocks
    private LogHelper logHelper;

    @Test
    public void it_should_convert_log_entries_to_logs_and_set_cause_to_block() throws ParseException {
        //Given
        LogEntry logEntry1 = new LogEntry();
        logEntry1.setIp("1.1.1.1");
        logEntry1.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 00:00:00.000"));
        logEntry1.setRequest("\"GET / HTTP/1.1\"");

        LogEntry logEntry2 = new LogEntry();
        logEntry2.setIp("2.2.2.2");
        logEntry2.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-02 00:00:00.000"));
        logEntry2.setRequest("\"POST / HTTP/1.1\"");

        LogEntry logEntry3 = new LogEntry();
        logEntry3.setIp("3.3.3.3");
        logEntry3.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-03 00:00:00.000"));
        logEntry3.setRequest("\"PUT / HTTP/1.1\"");

        LogEntry logEntry4 = new LogEntry();
        logEntry4.setIp("4.4.4.4");
        logEntry4.setDate(LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-04 00:00:00.000"));
        logEntry4.setRequest("\"DELETE / HTTP/1.1\"");

        List<LogEntry> logEntries = Arrays.asList(logEntry1, logEntry2, logEntry3, logEntry4);

        List<String> blockedIps = Arrays.asList("2.2.2.2", "3.3.3.3");

        OptionsResourcesDto optionsResourcesDto = new OptionsResourcesDto();
        optionsResourcesDto.setThreshold(1);
        optionsResourcesDto.setStartDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-02.00:00:00"));
        optionsResourcesDto.setEndDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-03.00:00:00"));

        //When
        List<Log> logs = logHelper.convertLogEntriesToLogsAndSetCauseToBlock(logEntries, blockedIps, optionsResourcesDto);

        //Then
        assertThat(logs).hasSize(4);
        assertThat(logs).extracting("ip").contains("1.1.1.1", "2.2.2.2", "3.3.3.3", "4.4.4.4");
        assertThat(logs).extracting("date").contains(
                LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 00:00:00.000"),
                LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-02 00:00:00.000"),
                LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-03 00:00:00.000"),
                LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-04 00:00:00.000")
        );
        assertThat(logs).extracting("request").contains(
                "\"GET / HTTP/1.1\"",
                "\"POST / HTTP/1.1\"",
                "\"PUT / HTTP/1.1\"",
                "\"DELETE / HTTP/1.1\""
        );

        //And
        for (Log log : logs) {
            if (log.getIp().equals("2.2.2.2") || log.getIp().equals("3.3.3.3")) {
                assertThat(log.getCauseToBlock()).isEqualTo("Ip blocked because of making 1 requests between dates" +
                        " 2017-01-02.00:00:00 and 2017-01-03.00:00:00");
            }
        }

    }
}