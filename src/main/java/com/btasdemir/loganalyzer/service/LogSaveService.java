package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.domain.Log;
import com.btasdemir.loganalyzer.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogSaveService {

    private final LogRepository logRepository;

    @Autowired
    public LogSaveService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void saveLogs(List<Log> logs) {
        logRepository.saveAll(logs);
    }

}
