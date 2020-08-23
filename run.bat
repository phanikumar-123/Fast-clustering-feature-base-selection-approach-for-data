set classpath=lib/jfreechart-1.0.13-swt.jar;lib/jfreechart-1.0.13-experimental.jar;lib/jfreechart-1.0.13.jar;lib/jcommon-1.0.16.jar;lib/weka.jar;lib/RerankingSearch.jar;.;
javac -d . *.java
java -Xmx1000M com.FastCluster
pause