package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.model.LogEntry;
import com.btasdemir.loganalyzer.model.dto.OptionsResourcesDto;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LogFiltererService {

    public List<String> filterBlockedIpsAndPrintToConsole(List<LogEntry> logEntries,
                                                          OptionsResourcesDto optionsResourcesDto) {
        return logEntries.stream()
                .filter(logEntry -> isLogEntryBetweenDates(logEntry, optionsResourcesDto))
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(LogEntry::getIp, Collectors.counting()),
                        filterMapByCounts(optionsResourcesDto)))
                .stream()
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    private Function<Map<String, Long>, Set<String>> filterMapByCounts(OptionsResourcesDto optionsResourcesDto) {
        return map -> filterIpsByCounts(map, optionsResourcesDto);
    }

    private Set<String> filterIpsByCounts(Map<String, Long> ipCounts, OptionsResourcesDto optionsResourcesDto) {
        ipCounts.values().removeIf(l -> l < optionsResourcesDto.getThreshold());
        return ipCounts.keySet();
    }

    private boolean isLogEntryBetweenDates(LogEntry logEntry, OptionsResourcesDto optionsResourcesDto) {
        return LogAnalyzerDateUtils.isDateEqualsOrAfter(logEntry.getDate(), optionsResourcesDto.getStartDate())
                && LogAnalyzerDateUtils.isDateEqualsOrBefore(logEntry.getDate(), optionsResourcesDto.getEndDate());
    }

}
