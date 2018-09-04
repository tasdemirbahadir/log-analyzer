package com.btasdemir.loganalyzer.util;

import com.btasdemir.loganalyzer.model.Duration;
import com.btasdemir.loganalyzer.resources.OptionsResources;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentsParserUtils {

    private static final String ACCESS_LOG = "accesslog";
    private static final String START_DATE = "startDate";
    private static final String DURATION = "duration";
    private static final String THRESHOLD = "threshold";

    public void parse(String[] args) throws ParseException, java.text.ParseException {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();
        CommandLine commandLine = parser.parse(options, args);
        fetchOptionsAndSetResources(commandLine);
    }

    private Options createOptions() {
        Options options = new Options();

        options.addOption(Option.builder()
                .longOpt(ACCESS_LOG)
                .required(true)
                .hasArg()
                .build());

        options.addOption(Option.builder()
                .longOpt(START_DATE)
                .required(true)
                .hasArg()
                .build());

        options.addOption(Option.builder()
                .longOpt(DURATION)
                .required(true)
                .hasArg()
                .build());

        options.addOption(Option.builder()
                .longOpt(THRESHOLD)
                .required(true)
                .hasArg()
                .build());

        return options;
    }

    private void fetchOptionsAndSetResources(CommandLine commandLine) throws java.text.ParseException {
        OptionsResources.setAccessLog(commandLine.getOptionValue(ACCESS_LOG));
        OptionsResources.setStartDate(LogAnalyzerDateUtils.parseCommandLineArgumentDateValue(commandLine.getOptionValue(START_DATE)));
        Duration duration = Duration.valueOf(commandLine.getOptionValue(DURATION).toUpperCase());
        OptionsResources.setEndDate(LogAnalyzerDateUtils.getEndDateAccordingTo(OptionsResources.getStartDate(), duration));
        OptionsResources.setThreshold(Integer.parseInt(commandLine.getOptionValue(THRESHOLD)));
    }

}
