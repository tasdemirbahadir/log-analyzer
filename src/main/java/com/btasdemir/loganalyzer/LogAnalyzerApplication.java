package com.btasdemir.loganalyzer;

import com.btasdemir.loganalyzer.util.ArgumentsParserUtils;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogAnalyzerApplication {

    public static void main(String[] args) throws ParseException, java.text.ParseException {
        ArgumentsParserUtils argumentsParserUtils = new ArgumentsParserUtils();
        argumentsParserUtils.parse(args);
        SpringApplication.run(LogAnalyzerApplication.class, args);
    }
}
