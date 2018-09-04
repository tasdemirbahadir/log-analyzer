package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.model.LogEntry;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.rule.OutputCapture;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class LogFileParserServiceTest {

    @InjectMocks
    private LogFileParserService logFileParserService;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void it_should_parse_log_file_properly() throws IOException, ParseException {
        //Given
        String logFilePath = "src/test/resources/test_access.log";

        //When
        List<LogEntry> parsedLogEntries = logFileParserService.parseLogFileLocatedAt(logFilePath);

        //Then
        assertThat(parsedLogEntries).hasSize(2);
        assertThat(parsedLogEntries).extracting("ip").contains("192.168.234.82", "192.168.234.82");
        assertThat(parsedLogEntries).extracting("date").contains(
                LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 00:00:11.763"),
                LogAnalyzerDateUtils.parseLogFileDateValue("2017-01-01 00:00:21.164")
        );
        assertThat(parsedLogEntries).extracting("request").contains("\"GET / HTTP/1.1\"", "\"GET / HTTP/1.1\"");
    }

    @Test
    public void it_should_log_error_and_set_date_field_with_null_value_when_date_field_has_invalid_value() throws IOException {
        //Given
        String logFilePath = "src/test/resources/test_access_with_wrong_date_value.log";

        //When
        List<LogEntry> parsedLogEntries = logFileParserService.parseLogFileLocatedAt(logFilePath);

        //Then
        outputCapture.expect(containsString("Date value of the log entry cannot be parsed with line:" +
                " 2017_01_01.00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version)" +
                " CFNetwork/808.2.16 Darwin/15.6.0\""));
        assertThat(parsedLogEntries.get(0).getIp()).isEqualTo("192.168.234.82");
        assertThat(parsedLogEntries.get(0).getDate()).isEqualTo(null);
        assertThat(parsedLogEntries.get(0).getRequest()).isEqualTo("\"GET / HTTP/1.1\"");
    }
}