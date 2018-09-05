package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.domain.Log;
import com.btasdemir.loganalyzer.model.LogEntry;
import com.btasdemir.loganalyzer.model.dto.OptionsResourcesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class LogAnalyzerService {

    private final LogFileParserService logFileParserService;
    private final LogFiltererService logFiltererService;
    private final LogSaveService logSaveService;
    private final LogHelper logHelper;
    private final OptionsResourcesService optionsResourcesService;

    @Autowired
    public LogAnalyzerService(LogFileParserService logFileParserService,
                              LogFiltererService logFiltererService,
                              LogSaveService logSaveService,
                              LogHelper logHelper,
                              OptionsResourcesService optionsResourcesService) {
        this.logFileParserService = logFileParserService;
        this.logFiltererService = logFiltererService;
        this.logSaveService = logSaveService;
        this.logHelper = logHelper;
        this.optionsResourcesService = optionsResourcesService;
    }

    public void analyzeLogs() throws IOException {
        String accessLogFilePath = optionsResourcesService.getAccessLog();
        List<LogEntry> parsedLogEntries = logFileParserService.parseLogFileLocatedAt(accessLogFilePath);
        filterLogEntriesAndPrintOnTheConsoleAndSaveIntoDatabase(parsedLogEntries);
    }

    private void filterLogEntriesAndPrintOnTheConsoleAndSaveIntoDatabase(List<LogEntry> logEntries) {
        OptionsResourcesDto optionsResourcesDto = optionsResourcesService.getOptionsResourcesDto();
        List<String> blockedIps = logFiltererService.filterBlockedIpsAndPrintToConsole(logEntries, optionsResourcesDto);
        List<Log> logs = logHelper.convertLogEntriesToLogsAndSetCauseToBlock(logEntries, blockedIps, optionsResourcesDto);
        logSaveService.saveLogs(logs);
    }

}
