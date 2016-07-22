# log-analyzer

LogAnalyzer is a simple and extendable Java app that processes log files and filter the relevant entries. To use it for your log files, create an own LogEntryParser. If the existing filters are not sufficient, you have to add the ones you need.

To execute the app run:

```
java -jar loganalyzer-1.3.1.jar --logFile test.log
```

That prints out the complete log file content because of no filters are defined. There is an example config.json file in src/test/resources. Depending on the complexity of your log files, defining all the necessary filters could get confusing. Therefore, if a config.json file lies in the directory as the app is started, the json is parsed and used like you have entered them as arguments. 

## License

LogAnalyzer is open-sourced software licensed under the [MIT license](http://opensource.org/licenses/MIT)
