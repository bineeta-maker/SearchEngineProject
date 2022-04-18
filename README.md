# SearchEngineProject

1. Download the project Folder
2. Copy the **.parquet**(all 4), **topics-2015-A.xml**, **dataindex**(unzipped) files in a desired location in local machine.
3. Update the desired location in **src\main\java\com\searchengine\utils\SEConstants.java**
4. From the project directory execute: **mvn clean package install assembly:assembly**
5. From the target directory(to create index to be executed if you want to create index): **java -jar SearchEngineProject-1.0-SNAPSHOT.jar createindex**
6. From the target directory(to search args[topicQuery, top n]): **java -jar SearchEngineProject-1.0-SNAPSHOT.jar 3 10**

# Reference:

[1] https://lucene.apache.org/core/9_1_0/index.html <br>
[2] https://trec.nist.gov/data/clinical2015.html <br>
[3] http://www.trec-cds.org/
