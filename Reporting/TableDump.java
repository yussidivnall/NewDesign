import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Iterator;
public class TableDump{
	static String root;
	static String columns;
	static String input_filename;
	static PrintStream output;
	static Document document;
	static String deliminator="\t\t";

	public static void dump(){
	//@ToDo use sprintf to format columns
		try{
			XPath xpath = XPath.newInstance(root);
			List list = xpath.selectNodes(document);
			Iterator iterator = list.iterator();
			String tags[] = columns.split(",");
			for (String s:tags){ //Print table header
				output.print(s+deliminator);
			}
			while(iterator.hasNext()){//print table
				Element e=(Element)iterator.next();
				output.println();
				for(int i=0;i<tags.length;i++){
					String s=e.getChild(tags[i]).getTextTrim();
					output.print(s+deliminator);	
				}
			}
			output.println();
		}catch(JDOMException je){
			je.printStackTrace();
		}	
	}


	public static void usage(){
		System.out.println("TableDump - dumps a table from a specified ValiDichro xml file");
		System.out.println("Usage:");
		System.out.println("java TableDump <-i Input_xml_filename> [Optionss]");
		System.out.println("Options:");
		System.out.println("	-o <Filename> or --output <Filename> : dump to this file (defaultis to standrad output if none given");
		System.out.println("	-c <Columns>  or --columns <Columns> : only print selected columns. columns must be a , seperated list of valid xml tags (default is all of CDTool's columns)");
		System.out.println("	-x <XPath>    or --xpath <XPath>     : an XPath expression for the root of the columns,"); 
		System.out.println("						must be a valid expression containing the columns as direct decendents");
		System.out.println("						(default /ValidichroData/ValidichroBody , the spectral entry table)");
		System.out.println("	-h    	      or --help		     : Prints this message and exit");
		System.out.println("	-i <Filename> or --input <Filename>  : Required: the input xml file to use");
		System.out.println("Note:");
		System.out.println("jdom and ParsingUtils must be in the java classpath, use ./runme.sh TableDump <Options> to compile and run with the correct classpath setup");
		System.out.println("Author: Yussi Divnal (UC.dev.null@Gmail.com)");
	}
	public static void parseArguments(String args[]){
		String outputfilename="";
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-i") || args[i].equals("--input")){
				input_filename=args[i+1];
				args[i+1]="";//How do i deal with arguments then? (HACK)
			}else if(args[i].equals("-o") || args[i].equals("--output")){
				outputfilename=args[i+1];
				args[i+1]="";//I hate this hack!
			}else if(args[i].equals("-h") || args[i].equals("--help")){
				usage();
				System.exit(0);
			}else if(args[i].equals("-c") || args[i].equals("--columns")){
				columns=args[i+1];
				args[i+1]="";//an ugly hack!
			}else if(args[i].equals("-x") || args[i].equals("--xpath")){
				root=args[i+1];
				args[i+1]="";//same ugly hack
			}else if(args[i].equals("")){//Same ugly hack
				continue;
			}else{
				System.out.println("Unknown argument : "+args[i]);
				usage();
				System.exit(1);
			}
		}	
		if(input_filename==null){
			System.out.println("No input filename given");
			usage();
			System.exit(1);
		}
		if(outputfilename!=""){
			try{
				output=new PrintStream(outputfilename);
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		if(!new File(input_filename).exists()){
			System.out.println("No such file:"+input_filename);
			usage();
			System.exit(1);
		}
		
	}
	public static void main(String args[]){
		root="/"+Parser.DATA_TAG+"/"+Parser.BODY_TAG+"/"+Parser.ENTRY_TAG; //defauls to selecting just spectral table
		columns=""+Parser.WAVELENGTH_TAG+","+Parser.SIGNAL_TAG+","+Parser.HIGHTENSION_TAG+","
			+Parser.SMOOTHSIGNAL_TAG+","+Parser.PSEUDOABSORBANCE_TAG+","+Parser.SAMPLE_STANDARDDEVIATION_TAG+","+Parser.BASELINE_STANDARDDEVIATION_TAG;//defauls to selecting all CDTool's columns
		output=System.out;//Defauls to print to standard output
		parseArguments(args);
		document=ParsingUtils.getXMLDocument(input_filename);
		dump();
	}
}
