package com.btasdemir.loganalyzer.listener;

import com.btasdemir.loganalyzer.service.LogAnalyzerService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartupApplicationListenerTest {

    @MockBean
    private LogAnalyzerService logAnalyzerService;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void it_should_analyze_logs_when_application_is_ready() throws IOException {
        verify(logAnalyzerService, times(1)).analyzeLogs();
    }
}