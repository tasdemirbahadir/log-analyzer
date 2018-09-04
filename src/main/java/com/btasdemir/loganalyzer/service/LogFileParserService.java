package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.model.LogEntry;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LogFileParserService {
    private static final Logger logger = LoggerFactory.getLogger(LogFileParserService.class);

    private static final int FIELDS_DATE_INDEX = 0;
    private static final int FIELDS_IP_INDEX = 1;
    private static final int FIELDS_REQUEST_INDEX = 2;

    public List<LogEntry> parseLogFileLocatedAt(String filePath) throws IOException {
        List<LogEntry> logEntries = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> logEntries.add(createLogEntryFrom(line)));
        }
        return logEntries;
    }

    private LogEntry createLogEntryFrom(String line) {
        String[] fields = line.split("\\|");
        LogEntry logEntry = new LogEntry();
        logEntry.setDate(parseLogEntryDateValue(fields[FIELDS_DATE_INDEX], line));
        logEntry.setIp(fields[FIELDS_IP_INDEX]);
        logEntry.setRequest(fields[FIELDS_REQUEST_INDEX]);
        return logEntry;
    }

    private Date parseLogEntryDateValue(String dateValue, String line) {
        try {
            return LogAnalyzerDateUtils.parseLogFileDateValue(dateValue);
        } catch (ParseException e) {
            logger.error("Date value of the log entry cannot be parsed with line: {}", line);
            return null;
        }
    }

}
