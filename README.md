# PieChartBayes

To build the snapshot jar

```shell
mvn clean install
```

To run a particular class

```shell
java -Djava.library.path=local-repo/ -classpath target/PieChart-0.0.1-SNAPSHOT.jar:local-repo/com/norsys/netica/5.0.4/netica-5.0.4.jar edu.piechart.<class-name>
```
