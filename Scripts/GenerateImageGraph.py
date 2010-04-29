#!/usr/bin/python
import sys;
import os;

def parseArguments():
	global tags;
	global outputImageFile;
	for i in range(len(sys.argv)):
		if sys.argv[i]=="-c" or sys.argv[i]=="--columns":
			tags=sys.argv[i+1].split(',');
			print tags
		if sys.argv[i]=="-o" or sys.argv[i]=="--output":
			outputImageFile=sys.argv[i+1];
			print outputImageFile
	return;
def makeTempTableFile():
	cols="";
	for i in tags:
		cols+=i+" ";
	os.system(tableMakingCommand+" "+xmlfilename+" "+cols+">"+tempTableFileName);
	return;


def plot():
	
	content="set terminal png x000000 xaaaaaa x10\n";
	content+="set output \""+outputImageFile +"\"\n"
	content+="plot ";
	for i in range(len(tags)):
		if(i==0): continue;
		content+="\'"+tempTableFileName+"\' using 1:"+str(i+1)+" with lines title \""+tags[i]+"\",";
	
	FILE = open(tempGPScript,"w");
	FILE.write(content.strip(","));
	FILE.close();
	os.system("gnuplot "+tempGPScript);
	return;

tags=["Wavelength","Signal","HighTension","SmoothSignal","PseudoAbsorbance","SampleStandardDeviation","BaselineStandardDeviation"];
outputImageFile="./graph.png";
tableMakingCommand="java -classpath ../externaljars/jaxen-1.1.2.jar:../externaljars/jdom.jar:./:../BuiltinParsers:../Reporting XPathTableExtract";
tempTableFileName="/tmp/tableout";
tempGPScript="/tmp/gpscript";

xmlfilename=sys.argv[1];
parseArguments();
makeTempTableFile();
plot();
print tags;
