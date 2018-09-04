package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.domain.Log;
import com.btasdemir.loganalyzer.model.LogEntry;
import com.btasdemir.loganalyzer.model.dto.OptionsResourcesDto;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogHelper {

    public List<Log> convertLogEntriesToLogsAndSetCauseToBlock(List<LogEntry> logEntryList,
                                                               List<String> blockedIps,
                                                               OptionsResourcesDto optionsResourcesDto) {
        return logEntryList.stream()
                .map(logEntry -> convertToLog(logEntry, blockedIps, optionsResourcesDto))
                .collect(Collectors.toList());
    }

    private Log convertToLog(LogEntry logEntry, List<String> blockedIps, OptionsResourcesDto optionsResourcesDto) {
        Log log = new Log();
        log.setDate(logEntry.getDate());
        log.setIp(logEntry.getIp());
        log.setRequest(logEntry.getRequest());
        setCauseToBlockIfInBlockedIpList(log, blockedIps, optionsResourcesDto);
        return log;
    }

    private void setCauseToBlockIfInBlockedIpList(Log log, List<String> blockedIpList,
                                                  OptionsResourcesDto optionsResourcesDto) {
        if (blockedIpList.contains(log.getIp())) {
            log.setCauseToBlock(String.format("Ip blocked because of making %d requests between dates %s and %s",
                    optionsResourcesDto.getThreshold(),
                    LogAnalyzerDateUtils.format(optionsResourcesDto.getStartDate()),
                    LogAnalyzerDateUtils.format(optionsResourcesDto.getEndDate())));
        }
    }

}
