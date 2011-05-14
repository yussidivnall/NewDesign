import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.InputStream;

import org.jdom.*;
import org.jdom.xpath.*;
import org.jdom.output.*;
import java.util.*;
public class Import{
	static PrintStream output;
	static BufferedReader input;
	static Document definitions;
	static Document outputDocument;
	static Element header;
	static Element body;
	static Element root;


	public static Element makeElement(Element def, String line){
		Element ret = new Element(def.getName());
		String delim = def.getAttribute("deliminator").getValue();
		int position= new Integer(def.getAttribute("value").getValue());
		String a[] = line.split(delim);
		ret.setText(a[position]);
		System.out.println("Val:"+a[position]);
		return ret;
	}
	public static void ParseHeaderLine(String l){
		try{
			//System.out.println("Header:"+l);
			Element parent_e= (Element)XPath.newInstance("//ValidichroHeader").selectSingleNode(definitions);
			List list = parent_e.getChildren();
			for(Object o:list){
				Element def_e = (Element)o;
				Attribute a = def_e.getAttribute("match");
				if(a!=null){
					String match= a.getValue();
					//System.out.println("Got match!"+match);
					if(l.matches(match)){
						System.out.println("Matched: "+l + "To " + match);
						Element element = makeElement(def_e,l);
						header.addContent((Content)element);
					}
				}
			}
		}catch(JDOMException je){je.printStackTrace();}
	}
	public static void ParseBodyLine(String l){}


	public static void Parse(){
		try{
			header = new Element("ValidichroHeader");
			body = new Element("ValidichroBody");
			root = new Element("ValidichroData");
			root.addContent(header);
			root.addContent(body);
			outputDocument = new Document(root);
			outputDocument.setRootElement(root);	
			String headerMatch=(String)((Attribute)XPath.newInstance("/FileFormatDefinition/ValidichroHeader/@match").selectNodes(definitions).get(0)).getValue();
        	        String bodyMatch=(String)((Attribute)XPath.newInstance("/FileFormatDefinition/ValidichroBody/@match").selectNodes(definitions).get(0)).getValue();
			outputDocument = new Document();
			String l;
			while((l = input.readLine())!=null){
				if(l.matches(headerMatch)){
					ParseHeaderLine(l);
				}
				if(l.matches(bodyMatch)){
					ParseBodyLine(l);
				}
			}
			//writeOutput();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch(JDOMException je){
			je.printStackTrace();
		}
		
	}

	public static void writeOutput() throws IOException{
		XMLOutputter xout = new XMLOutputter();
		xout.setFormat(Format.getPrettyFormat());
		xout.output(outputDocument,output);
		output.close();
	}



	/*
	*	Shell startup
	*/

	public static void Usage(){
		System.out.println("Import - Imports a Circular dichrosis file");
		System.out.println("Usage:");
		System.out.println("java Import <-i input file> <-d definition file> [-o output file]");
	}
	public static void ParseArguments(String arguments[]){
		String out="";
		String in="";
		String def="";
		for(int i=0;i<arguments.length;i++){
			if(arguments[i].equals("-h") || arguments[i].equals("--help")){
				Usage();
				System.exit(0);
			}else if(arguments[i].equals("")){
				continue;
			}else if(arguments[i].equals("-i") || arguments[i].equals("--input")){
				in=arguments[i+1];
				arguments[i+1]="";
			}else if(arguments[i].equals("-o") || arguments[i].equals("--output")){
			}else if(arguments[i].equals("-d") || arguments[i].equals("--definition")){
				def=arguments[i+1];
				arguments[i+1]="";
			}else{
				System.out.println("Unknown argument: "+arguments[i]);
				Usage();
				System.exit(1);
			}
		}
		if(in.equals("")){
			System.out.println("No input file specified");
			Usage();
			System.exit(1);
		}else  if(!new File(in).exists()){
			System.out.println("No such file: "+in);
			Usage();
			System.exit(1);
		}else{
			try{
				FileInputStream fis = new FileInputStream(in);
				InputStreamReader isr = new InputStreamReader((InputStream)fis);
				input = new BufferedReader((Reader)isr);
			}catch(IOException  ioe){
				ioe.printStackTrace();
			}
		}
		//Default: print to System.out;
		if(out.equals("")){
			output=System.out;
		}else{
			try{
			output = new PrintStream(new File(out));
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		if(def.equals("")){
			System.out.println("No definition file specified");
			Usage();
			System.exit(1);
		}else if (!new File(def).exists()){
			System.out.println("No such definition file: "+def);
			Usage();
			System.exit(1);
		}else{
			definitions = ParsingUtils.getXMLDocument(def);
		}
	}
	public static void main(String args[]){
		ParseArguments(args);
		Parse();
	}
}
