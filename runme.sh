#!/bin/bash
#Runs something with main(...)
#
#
#
CLASSES="./:./DataStructure/:externaljars:Parsers";

javac Loader.java -classpath $CLASSES &&
java -cp $CLASSES Loader dump1.txt
