# PieChartBayes

This maven project is for the Mac OSX version of Netica found at: https://www.norsys.com/netica-j.html#download

The local maven repo was generated with the following command:

```
$ mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=path/to/netica/NeticaJ.jar -DgroupId=com.norsys -DartifactId=netica -Dversion=5.0.4 -Dpackaging=jar -DlocalRepositoryPath=./local-repo
```
It holds the Netica dependencies libnetica.a and libNeticaJ.jnilib that maven can not find. 

To build the snapshot jar:

```
$ mvn clean install
```

To run a particular class:

```
$ java -Djava.library.path=local-repo/ -classpath target/PieChart-0.0.1-SNAPSHOT.jar:local-repo/com/norsys/netica/5.0.4/netica-5.0.4.jar main.java.edu.piechart.<class-name>
```


When creating a new class that uses Netica objects, you must set the Eclipse/IntelliJ vm arguments under run configurations to use the Netica library.
These files are in the `local-repo` and we must set the `<absolute-path-to>` your absolute path.
```
-Djava.library.path=/<absolute-path-to>/pie-chart-bayes/local-repo
```

![illustration](docs/vm-arguments.png)

You can add the Netica .dne files to the .gitignore. The time stamp is updated in the file every time the .java file is run. 
From the project path repository:
```
$ nano .gitignore
```
Place in file:
```
piechart_bayes_data/NetFiles/*
```
Then run:
```
$ git rm -r —cached piechart_bayes_data/NetFiles/
```
```
$ git update-index —assume-unchanged piechart_bayes_data/NetFiles/
```
