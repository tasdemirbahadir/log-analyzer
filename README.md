[![Build Status](https://travis-ci.org/tasdemirbahadir/log-analyzer.svg?branch=master)](https://travis-ci.org/tasdemirbahadir/log-analyzer)
<br>

**Log Analyzer**

Log Analyzer application analyzes the given log files, detects ips to block according to the given time, duration and threshold and uploads the log entries into H2 in memory database.

**Usage**

To run the application, set below program arguments to run configuration:

`--accesslog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100`

**Running Jar**

To run the generated jar, use the below command:

`java -cp "log-analyzer-0.0.1-SNAPSHOT.jar" org.springframework.boot.loader.JarLauncher --accesslog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100`

**Validating Application**

After you run the app with the given program arguments, head to a browser and navigate to:

`http://localhost:8080/h2-console`

Login with credentials:

`username=sa`

`password=sa`

Run below SQL query and see if the same IPs that are printed on the application console:

`select count(*) count, l.ip ip from log l where l.date >= '2017-01-01 13:00:00.000' and l.date <= '2017-01-01 14:00:00.000' group by l.ip having count >= 100;`

Run below SQL queries for the request info of the two IPs found as the result of the filtering:

`select l.ip ip, l.date date, l.request request from log l where l.ip = '192.168.228.188';`

`select l.ip ip, l.date date, l.request request from log l where l.ip = '192.168.77.101';`

**Schema of the Table 'Log' Used In MySQL**

You can checkout the table schema used for the table 'log' below.

id | ip | date | request | cause_to_block
--- | --- | --- | --- |---
int8 | varchar(50) | datetime | varchar(255) | varchar(255)
primary key | | | |