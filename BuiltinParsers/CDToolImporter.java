import org.jdom.xpath.XPath;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.*;
import org.jdom.JDOMException;
import java.io.*;

public class CDToolImporter{
	String definitionFile=config.DefinitionFiles+"CDToolFileDefinition.xml";
	public CDToolImporter(String infile,String outfile){
		try{
			Document definition=ParsingUtils.getXMLDocument(definitionFile);
			String headerMatch=(String)((Attribute)XPath.newInstance("/FileFormatDefinition/ValidichroHeader/@match").selectNodes(definition).get(0)).getValue();
			String bodyMatch=(String)((Attribute)XPath.newInstance("/FileFormatDefinition/ValidichroBody/@match").selectNodes(definition).get(0)).getValue();
			Element headerDefinition=(Element)XPath.newInstance("/FileFormatDefinition/ValidichroHeader").selectSingleNode(definition);
			Element bodyDefinition=(Element)XPath.newInstance("/FileFormatDefinition/ValidichroBody").selectSingleNode(definition);
			FileInputStream fis = new FileInputStream(infile);
			InputStreamReader isr = new InputStreamReader((InputStream)fis);
			BufferedReader br = new BufferedReader((Reader)isr);
			String l;
			while((l=br.readLine())!=null){
				if(l.matches(headerMatch)){
				//	System.out.println("Header:"+l);
				}
				else if(l.matches(bodyMatch)){
				//	System.out.println("Body:"+l);
				}
				else{
					System.out.println("Unmatched:"+l);
				}
			//	System.out.println(l.split(deliminator)[0]);
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch (JDOMException je){
			je.printStackTrace();
		}
	}







	static String in="";
	static String out="";
	public static void usage(){
		System.out.println("CDToolImporter");
		System.out.println("Usage:");
	}	
	public static void parseArguments(String as[]){
		String arguments[]=as;
		//if(arguments.length <3){
		//	System.out.println("Not enough arguments");
		//	usage();
		//	System.exit(1);
		//}
		for(int i=0;i<arguments.length;i++){
			if(arguments[i].equals("-i") || arguments[i].equals("--input")){
				in=arguments[i+1];
				arguments[i+1]="";
			}else if(arguments[i].equals("-o") || arguments[i].equals("--output")){
				out=arguments[i+1];
				arguments[i+1]="";
			}else if(arguments[i].equals("-h") || arguments[i].equals("--help")){
				usage();
				System.exit(0);
			}else if(arguments[i].equals("")){	
				continue;
			}else{
				System.out.println("Unknown argument:"+arguments[i]);
				usage();
				System.exit(1);
			}
		}		
			if(in.equals("") || out.equals("")){
				System.out.println("No files given, Input:"+in+" output:"+out);
				usage();
				System.exit(1);
			}
			if(!new File(in).exists()){
				System.out.println("No such file:"+in);
				usage();
				System.exit(1);
			}
		
	}
	public static void main(String args[]){
		parseArguments(args);
		CDToolImporter me = new CDToolImporter(in,out);	
	}
}
