**Log Analyzer**

Log Analyzer application analyzes the given log files, detects ips to block according to the given time, duration and threshold.

**Usage**

To run the application, set below program arguments to run configuration:

`--accesslog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100`

**Running Jar**

To run the generated jar, use the below command:

`java -cp "log-analyzer-0.0.1-SNAPSHOT.jar" com.btasdemir.loganalyzer.LogAnalyzerApplication --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100`