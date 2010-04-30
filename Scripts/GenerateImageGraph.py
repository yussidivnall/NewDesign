#!/usr/bin/python
import sys;
import os;

#Defauls to taking all of the relevant CDTool column names
Columns="Wavelength,Signal,HighTension,SmoothSignal,PseudoAbsorbance,SampleStandardDeviation,BaselineStandardDeviation";
#Defaults image file to be:
Output="./graph.png";
#Defauls to taking all <SpectralEntry> in file
XPathToParent="//ValidichroBody//SpectralEntry";
Input="";
def Usage():
	print"""
		GenerateImageGraph - Uses gnuplot to plot a graph of an XML input file.
		Usage:
			GenerateImageGraph <-i XML_file> [Options]
		Options:
			-c or --columns : Select the column names, the first column will be used for the X axis 	
					  (Defaults to all CDTool's columns)
			-o or --output  : Specify the output png image file 
					  (Defaults to ./graph.png)
			-x or --xpath	: Specify the XPath to the parent of the selected columns,
					  (Defaults to all SpectralEntry tags \"//ValidichroBody//SpectralEntry\"
			-i or --input	: REQUIRED, Specify the XML file to use.
			-h or --help	: Print this help message and quit
	""";
	return;

def ParseCommandlineArguments():
	Arguments = sys.argv;
	global Columns;
	global Output;
	global XPathToParent;
	global Input;
	for i in range(len(Arguments)):
		if(i==0):continue;
		if Arguments[i]=="-c" or Arguments[i]=="--columns":
			Columns=Arguments[i+1]
			Arguments[i+1]="";
		elif Arguments[i]=="-o" or Arguments[i]=="--output":
			Output=Arguments[i+1];
			Arguments[i+1]="";
		elif Arguments[i]=="-x" or Arguments[i]=="--xpath":
			XPathToParent=Arguments[i+1];
			Arguments[i+1]="";
		elif Arguments[i]=="-i" or Arguments[i]=="--input":
			Input=Arguments[i+1];
			Arguments[i+1]="";
		elif Arguments[i]=="-h" or Arguments[i]=="--help":
			Usage();
			sys.exit(0);
		elif Arguments[i]=="":continue;
		else:
			print "unknown argument:"+Arguments[i];
			Usage();
			sys.exit(1);
	if(Input==""):
		print "No input table file specified, requires a -i <XML file name>";
		Usage();
		sys.exit(1);
	return;
	print Output;

def MakeTable():
	command="java -classpath ../externaljars/jaxen-1.1.2.jar:../externaljars/jdom.jar:./:../Reporting:../BuiltinParsers TableDump -i "+Input+" -c "+Columns+" -x "+XPathToParent+" -o /tmp/table_out";
	os.popen(command);
	return;

def Plot():
	cols=Columns.split(",");
	content="set terminal png x000000 xaaaaaa x10\n";
	content+="set xlabel \""+cols[0]+"\" \n";
        content+="set output \""+Output+"\"\n"
        content+="plot ";
	for i in range(len(cols)):
                if(i==0): continue;
                content+="\'"+"/tmp/table_out"+"\' using 1:"+str(i+1)+" with lines title \""+cols[i]+"\",";

        FILE = open("/tmp/gnuplot_script","w");
        FILE.write(content.strip(","));
        FILE.close();
        os.system("gnuplot "+"/tmp/gnuplot_script");
	return;

ParseCommandlineArguments();
MakeTable();
Plot();
os.system("display "+Output);
