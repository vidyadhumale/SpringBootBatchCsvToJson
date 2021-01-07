# SpringBootBatchCsvToJson

CSV to JSON writing to file

Software :

java : 13

Spring : 2.4.2-SNAPSHOT

apache-maven-3.6.3

Tomcat: Default Spring boot 

======================================================================

Steps:

Check out project from "https://github.com/vidyadhumale/SpringBootBatchCsvToJson.git"
Execute the Main class : SpringBatchCsvToJsonApplication.java

Run the below rest services through POSTMAN
====================================================================

GET:  http://localhost:8080/load

Response : It will return the staus of JOB "COMPLETED" / "FAILED" and CSV data will written to employees.json file.
If data is invalid, it will log error in log file for that data.
 
