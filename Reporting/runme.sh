#!/bin/bash
#Usage: ./runme.sh <TESTCLASS> <XMLFILE> <MINIMUM_WAVELENGTH> <DEVIATION_FROM_ZERO>
javac -classpath ../externaljars/jaxen-1.1.2.jar:../externaljars/jdom.jar:./:../BuiltinParsers $1".java" &&
java -classpath ../externaljars/jaxen-1.1.2.jar:../externaljars/jdom.jar:./:../BuiltinParsers $@
