package com.btasdemir.loganalyzer.listener;

import com.btasdemir.loganalyzer.service.LogAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StartupApplicationListener {
    private final LogAnalyzerService logAnalyzerService;

    @Autowired
    public StartupApplicationListener(LogAnalyzerService logAnalyzerService) {
        this.logAnalyzerService = logAnalyzerService;
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onApplicationEvent(ApplicationReadyEvent contextRefreshedEvent) throws IOException {
        logAnalyzerService.analyzeLogs();
    }

}
