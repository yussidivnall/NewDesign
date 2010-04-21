package uc.validichro.parsers
import java.io.*;
import java.math.*;
import java.util.*;
/*
*	CDToolParser - Parses CDToolData files as Validichro Data XML files
*
*	This class it temopray, to be done with perl or XPath/XSSLT/...
*	Important development notes:
*		-Only takes values of first,second and third columns(out of 7) of spectral data
*		 I i'm not sure if those are in this order (wavelength,smooth,unsmooth)
*		-Need to sort the casting of elements to unparsed_lines ->Causes warning : fixed?
*	Usage, 
*		From shell: java CDToolParser <Infile> [Outfile]
*		if no Outfile then print on System.out, @To Do
*		if Infile doen't esist then exit @To DO
*
*		from java 
*		import <this Package name>.CDToolParser
*		new CDToolParser("Infile",Outfile")
*
*/

/*	Dev: this is incomplete, it puts all the lines it doesn't know how to deal with under <UnParsed>.
*		I don't know what values go in the table I think the first few are wavelength,Smooth,Unsmooth, but I'm not sure
*		This should really take strings for the tagnames from somewhere else, probably best from Parser.STRING_CONSTANTS, of maybe from a resource of some sort.
*
*	UC.dev.null@gmail.com
*/	

public class CDToolParser{
	String experiment_date;
	String generic;
	String experiment;
	String code;
	String low_wavementh;
	String high_wavelength;
	String interval_nm;
	String machine;
	String dwell_time_s;
	String cutoff_nm;

	PrintStream out;
	ArrayList <String>spectral_table;
	ArrayList <String>unparsed_lines;
	


	public void WriteHeader(){
		out.println("\t\t"+Parser.StartingTag("ValidichroHeader"));
		out.println("\t\t\t"+Parser.Tag("Date",experiment_date));
		out.println("\t\t\t"+Parser.Tag("Generic",generic));
		//...
		out.println("\t\t"+Parser.EndingTag("ValidichroHeader"));
	}
	public void WriteBody(){
		//@ToDo : Make this proper java!
		//@ToDo Important:  Add missing elements and check existing ones
		//	-	Update:, now refers to values as Unknown1,Uknown2... It's just unknown to me, can't remember what these values represent!
		out.println("\t\t"+Parser.StartingTag("ValidichroBody"));
		for(String line :spectral_table){
			out.println("\t\t\t"+Parser.Tag("SpectralEntry",line));
		}
		out.println("\t\t"+Parser.EndingTag("ValidichroBody"));
	}
	public void WriteUnparsed(){
		//@ToDo : Make this proper java!
		out.println("           <ValidichroUnparsed>");
		int s = unparsed_lines.size();
		for(int i=0;i<s;i++){
			String line=(String)unparsed_lines.get(i);
			out.println("           	<ValidichroUnparsedLine>"+line+"</ValidichroUnparsedLine>");
		}
		out.println("           </ValidichroUnparsed>");
	}
	public void WriteExported(){
		out.println(Parser.getFileHeader());
		out.println("\t"+Parser.StartingTag("ValidichroData"));
		WriteHeader();
		WriteBody();
		WriteUnparsed();
		out.println("\t"+Parser.EndingTag("ValidichroData"));
	}
        public void ParseSpectralEntry(String line){
		//@ToDo, take all values (there are 7)
		//	Update	-	now takes all values, but calls them Unknown1,2,3,4 I need to find out what these values represent!
		//Not production safe!
		String table_ent=Parser.getTableRow(line,new String[]{"WaveLength","Smooth","Unsmooth","Unknown1","Unknown2","Uknown3","Unknown4"});
		spectral_table.add(table_ent);
        }
	public void ParseUnrecognisedLines(String l){
		//This should not be nececarry, only temporary until I deal with all the data
		unparsed_lines.add((String)l);
	}

	public String ParseLine(String line){
		String ret="";
		if(line.startsWith("Generic"))generic=line.split("\\s+")[1];
		else if(line.startsWith("Date"))experiment_date=line.split("\\s+")[1];
		else if(line.startsWith("Experiment"))experiment=line.split("\\s+")[1];
 		else if(line.startsWith("Code"))code=line.split("\\s+")[1];
		else{
			if(line.matches("^[\\d]+.[\\d]+.*")){ //Line is starting with two numerical values (needs to check it's 7?)
				//Treat as spectral data
				ParseSpectralEntry(line);	
			}else{
				ParseUnrecognisedLines(line);
                        }
		
		}
		return ret;
	}
	/*
	*	Constructor (class entry point from java)
	*
	*/
	CDToolParser(String infilename, String outfile){
		try{
			//@ToDo make outfile stream, this prints to System.out instead
			out=System.out;
			
			
			unparsed_lines = new ArrayList<String>();
			//spectral_entries = new ArrayList<SpectralEntry>();
			spectral_table = new ArrayList<String>();

			//File f = new File(infilename);
			FileInputStream fis= new FileInputStream(infilename);
			InputStreamReader isr = new InputStreamReader((InputStream)fis);
			BufferedReader br = new BufferedReader((Reader)isr);
			String line="";
			while((line=br.readLine())!=null){
				String p=ParseLine(line);
			}
			WriteExported();
			
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	/*
	*	Methods for shell startup.
	*
	*/

	public static void usage(){
	}
	public static void main(String args[]){
		String infilename = args[0];
		String outfilename= args[1];
		if(args.length <1 || args.length >=3){
			usage();
			System.exit(2);
		}
		if(!new File(infilename).exists()){
			System.out.println("No such file:"+infilename);
			System.exit(1);
		}
		if(new File(outfilename).exists()){
			//To Overwrite?
		}
		System.out.println("write:"+infilename+">"+outfilename);
		//@ToDo - Check arguments are valid files...
		 

		CDToolParser me=new CDToolParser(infilename,outfilename);
	}
}
