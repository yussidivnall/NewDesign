#!/bin/bash
filename=$1;
outfilename=$2
java -classpath ../BuiltinParsers/ CDToolParser $filename $outfilename
