package com.btasdemir.loganalyzer.service;

import com.btasdemir.loganalyzer.domain.Log;
import com.btasdemir.loganalyzer.repository.LogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogSaveServiceTest {

    @InjectMocks
    private LogSaveService logSaveService;

    @Mock
    private LogRepository logRepository;

    @Test
    public void it_should_save_logs() {
        //Given
        List<Log> logs = new ArrayList<>();

        //When
        logSaveService.saveLogs(logs);

        //Then
        verify(logRepository, times(1)).saveAll(logs);
    }
}