#!/bin/bash
java -classpath ../externaljars/jaxen-1.1.2.jar:../externaljars/jdom.jar:./ XPathExamples $1 > /tmp/graphplot.txt
./plottingscript.plt
gthumb ./out.png

