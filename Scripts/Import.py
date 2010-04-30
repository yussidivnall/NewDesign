#!/usr/bin/python
import sys;
import os;
Outdir="../xmls/";
for s in sys.argv:
	elements=s.split("/");
	en=len(elements);
	fn=elements[en-1];
	command = "java -classpath ../BuiltinParsers/ CDToolParser "+s +" "+Outdir+fn+".xml"
	print command;
	os.system(command);
