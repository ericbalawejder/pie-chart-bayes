# PieChartBayes

The local maven repo was generated with the following command:

```shell
$ mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=path/to/netica/NeticaJ.jar -DgroupId=com.norsys -DartifactId=netica -Dversion=5.0.4 -Dpackaging=jar -DlocalRepositoryPath=./local-repo
```

To build the snapshot jar:

```shell
$ mvn clean install
```

To run a particular class:

```shell
$ java -Djava.library.path=local-repo/ -classpath target/PieChart-0.0.1-SNAPSHOT.jar:local-repo/com/norsys/netica/5.0.4/netica-5.0.4.jar edu.piechart.<class-name>
```
