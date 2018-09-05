package com.btasdemir.loganalyzer;

import com.btasdemir.loganalyzer.resources.OptionsResources;
import com.btasdemir.loganalyzer.util.LogAnalyzerDateUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogAnalyzerApplicationIT {

    @BeforeClass
    public static void setup() throws ParseException {
        OptionsResources.setAccessLog("src/test/resources/test_access.log");
        OptionsResources.setStartDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.13:00:00"));
        OptionsResources.setEndDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue("2017-01-01.14:00:00"));
        OptionsResources.setThreshold(100);
    }

    @Test
    public void it_should_load_context() {
    }

}
