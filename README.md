# PieChartBayes

This maven project is for the Mac OSX version of Netica found at: https://www.norsys.com/netica-j.html#download

The local maven repo was generated with the following command:

```shell
$ mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=path/to/netica/NeticaJ.jar -DgroupId=com.norsys -DartifactId=netica -Dversion=5.0.4 -Dpackaging=jar -DlocalRepositoryPath=./local-repo
```
It holds the Netica dependencies libnetica.a and libNeticaJ.jnilib that maven can not find. 

To build the snapshot jar:

```shell
$ mvn clean install
```

To run a particular class:

```shell
$ java -Djava.library.path=local-repo/ -classpath target/PieChart-0.0.1-SNAPSHOT.jar:local-repo/com/norsys/netica/5.0.4/netica-5.0.4.jar edu.piechart.<class-name>
```


When creating a new class that uses Netica objects, you must change the Eclipse vm arguments under run configurations to the Netica library:
```shell
-Djava.library.path=/<yourpath>/Netica/NeticaJ_504/bin
```

You can add the Netica .dne files to the .gitignore. The time stamp is updated in the file every time the .java file is run. 
From the project path repository:
```shell
$ nano .gitignore
```
Place in file:
```shell
piechart_bayes_data/NetFiles/*
```
Then run:
```shell
$ git rm -r —cached piechart_bayes_data/NetFiles/
```
```shell
$ git update-index —assume-unchanged piechart_bayes_data/NetFiles/
```
