#!/usr/bin/python
import sys;
import os;

def LoadOptions():
        global Output;
        command="java -classpath ../ config ";
        arg="getXMLPath";
        FILE = os.popen(command+arg);
        for l in FILE:
                Output=l.strip("\n");
        print Output;

Output="";
LoadOptions();
#Outdir=".../../xmls/";
for s in sys.argv:
	elements=s.split("/");
	en=len(elements);
	fn=elements[en-1];
	command = "java -classpath ../BuiltinParsers/ CDToolParser "+s +" "+Output+fn+".xml"
	print command;
	#os.system(command);
