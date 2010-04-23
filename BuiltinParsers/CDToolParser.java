/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author volcan
 */
import java.io.*;
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
*               Temporarly this would not dump to standard output if not given outfile,
*
*	UC.dev.null@gmail.com
*/

public class CDToolParser{
	//Match lines begining with [x][0] with tags [x][1]
        String LabelValuePairs[][]={
                                   {"Generic",Parser.GENERIC_TAG},
                                   {"Date",Parser.DATE_TAG},
                                   {"Experiment",Parser.PROTEIN_NAME_TAG},
                                   {"Code",Parser.CODE_TAG},
                                   {"Low Wavelength",Parser.LOW_WAVELENGTH_TAG},
                                   {"High Wavelength",Parser.HIGH_WAVELENGTH_TAG},
                                   {"Machine",Parser.MACHINE_TAG},
                                   {"Cell ID",Parser.CELL_ID_TAG},
                                   {"Smoothing window",Parser.SMOOTHING_WINDOW_TAG},
                                   {"Temperature",Parser.TEMPERATURE_TAG},
                                   {"PDB",Parser.PDB_TAG},
				   {"Calibration file",Parser.CALIBRATION_FILE_TAG},
				   {"Units",Parser.UNITS_TAG},
				   {"Zeroed between",Parser.ZEROED_BETWEEN_TAG},
				   {"Baselines",Parser.BASELINE_TAG},
				   {"Samples",Parser.SAMPLES_TAG},
				   {"Description",Parser.DESCRIPTION_TAG}
                                   };
	//Match lines begining with [x][0] with unit [x][1] and tag [x][2]
        String LabelUnitValue[][]={
                                    {"Interval (nm)","nm",Parser.INTERVAL_TAG},
                                    {"Cut off (nm)","nm",Parser.CUTOFF_TAG},
                                    {"Dwell time (s)","s",Parser.DWELLTIME_TAG},
                                    {"Pathlength (cm)","cm",Parser.PATHLENGTH_TAG},
                                    {"Concentration (mg/ml)","mg/ml",Parser.CONCENTRATION_TAG},
                                    {"M.R.W. (Da)","Da",Parser.MRW_TAG}
                                 };


	PrintStream out;

        ArrayList <String> header_lines;
	ArrayList <String>spectral_table;
	ArrayList <String>unparsed_lines;


        public void ExportHeader(){
            Iterator itr = header_lines.iterator();
            while(itr.hasNext()){
                String l = (String)itr.next();
                out.println("\t\t"+l);
            }
        }
        public void ExportBody(){
            Iterator itr = spectral_table.iterator();
            while(itr.hasNext()){
                String l = (String)itr.next();
                out.println("\t\t"+l);
            }
        };
        public void ExportUnparsed(){
            Iterator itr = unparsed_lines.iterator();
            while(itr.hasNext()){
                String l = (String)itr.next();
                out.println("\t\t"+l);
            }
        }

        public void Export(){
            out.println(Parser.getFileHeader());
               out.println(Parser.StartingTag(Parser.DATA_TAG));
                    out.println("\t"+Parser.StartingTag(Parser.HEADER_TAG));
                    ExportHeader();
                    out.println("\t"+Parser.EndingTag(Parser.HEADER_TAG));

                    out.println("\t"+Parser.StartingTag(Parser.BODY_TAG));
                    ExportBody();
                    out.println("\t"+Parser.EndingTag(Parser.BODY_TAG));
                    //Maybe add Unparsed lines later!
                    out.println("\t"+Parser.StartingTag(Parser.UNPARSED_TAG));
                    ExportUnparsed();
                    out.println("\t"+Parser.EndingTag(Parser.UNPARSED_TAG));
               out.println(Parser.EndingTag(Parser.DATA_TAG));
        }

        public boolean ParseSpectralEntry(String line){
		//CDTool dataformat taken from:
		//http://cdtools.cryst.bbk.ac.uk/
		//Column:
		//Wavelength,CDSignal,HighTensionSignal,CDSmoothSignal,PseudoAbsorbance,StandardDeviationSampleScan,StandardDeviationBaselineScans
                if(line.matches("^[\\d]+.[\\d]+.*")){ //Line is starting with two numerical values (needs to check it's 7?)
			String vals[] = line.split("\\s+");
                    	String WaveLength=vals[0];
		    	String CDSignal=vals[1];
			String HighTension=vals[2];
			String SmoothSignal=vals[3];
			String PseudoAbsorbance=vals[4];
			String SampleStandardDeviation=vals[5];
			String BaselineStandardDeviation=vals[6];
			String xmlLine=Parser.StartingTag(Parser.ENTRY_TAG)+
					Parser.Tag(Parser.WAVELENGTH_TAG,WaveLength)+
					Parser.Tag(Parser.SIGNAL_TAG,CDSignal)+
					Parser.Tag(Parser.HIGHTENSION_TAG,HighTension)+
					Parser.Tag(Parser.SMOOTHSIGNAL_TAG,SmoothSignal)+
					Parser.Tag(Parser.PSEUDOABSORBANCE_TAG,PseudoAbsorbance)+
					Parser.Tag(Parser.SAMPLE_STANDARDDEVIATION_TAG,SampleStandardDeviation)+
					Parser.Tag(Parser.BASELINE_STANDARDDEVIATION_TAG,BaselineStandardDeviation)+
					Parser.EndingTag(Parser.ENTRY_TAG);
			spectral_table.add(xmlLine);
		    /*String wavelength=vals[0];
                    String smooth=vals[1];
                    String unsmooth=vals[2];
                    String v1=vals[3];
                    String v2=vals[4];
                    String v3=vals[5];
                    String v4=vals[6];
                    String xmlLine=Parser.StartingTag(Parser.ENTRY_TAG)+
                            Parser.Tag(Parser.WAVELENGTH_TAG, wavelength)+
                            Parser.Tag(Parser.SMOOTH_TAG, smooth)+
                            Parser.Tag(Parser.UNSMOOTH_TAG, unsmooth)+
                            Parser.Tag(Parser.VAL1, v1)+
                            Parser.Tag(Parser.VAL2, v2)+
                            Parser.Tag(Parser.VAL3, v3)+
                            Parser.Tag(Parser.VAL4, v4)+
                            Parser.EndingTag(Parser.ENTRY_TAG);
                    spectral_table.add(xmlLine);
		    */
                    return true;
                }
                 return false;
        }
	public void ParseUnrecognisedLines(String l){
		//This should not be nececarry, only temporary until I deal with all the data
		unparsed_lines.add((String)l);
	}

        //For Header
        public boolean ParseLabelValuePairs(String l){
            int length=LabelValuePairs.length;
            for(int i=0;i<length;i++){
                if(l.startsWith(LabelValuePairs[i][0])){
                //Found a mathing LabelValue pair
                    String tag=LabelValuePairs[i][1];
                    //Collect all the rest of the values
                    String sub = l.substring(LabelValuePairs[i][0].length(), l.length());
                    String val = sub.trim();
                    header_lines.add(Parser.Tag(tag, val));
                    return true;
                }
            }
            return false;
        }

        //For headers elements with additional info
        //(such as units <ELEMENT unit="...">Value</ELEMENT>
        public boolean ParseComplexHeaderLine(String l){
            int length=LabelUnitValue.length;
            for(int i=0;i<length;i++){
                if(l.startsWith(LabelUnitValue[i][0])){
                    String unit=LabelUnitValue[i][1];
                    String tag=LabelUnitValue[i][2];
                    String s = l.substring(LabelUnitValue[i][0].length(),l.length());
                    String val=s.trim();
                    //Create a <ELEMENT unit="...">VAL</ELEMENT> line
                    String xmlLine=Parser.StartingTag(tag+" unit=\""+unit+"\"")+val+Parser.EndingTag(tag);
                    header_lines.add(xmlLine);
                    return true;
                }
            }
            return false;
        }


	public void ParseLine(String line){
                if(!ParseLabelValuePairs(line)){ //If it's not a Label Value pair
                    if(!ParseComplexHeaderLine(line)){ // It can be a Complex Header Line
                        if(!ParseSpectralEntry(line)){//And if not that could it be a spectral entry?
                            ParseUnrecognisedLines(line); //Then it's an unrecognised line!
                        }
                    }
                }
	}
	/*
	*	Constructor (class entry point from java)
	*
	*/
	CDToolParser(String infile, String outfile){
		try{
			//@ToDo make outfile stream, this prints to System.out instead
			//out=System.out;
                        out= new PrintStream(new File (outfile));

			unparsed_lines = new ArrayList<String>();
			//spectral_entries = new ArrayList<SpectralEntry>();
			spectral_table = new ArrayList<String>();
                        header_lines = new ArrayList<String>();
			//File f = new File(infilename);
			FileInputStream fis= new FileInputStream(infile);
			InputStreamReader isr = new InputStreamReader((InputStream)fis);
			BufferedReader br = new BufferedReader((Reader)isr);
			String line="";
			while((line=br.readLine())!=null){
				ParseLine(line);
			}
                        Export();

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
            System.out.println("CDTool Parser");
            System.out.println("Parses CDTool format files into an xml format");
            System.out.println("Usage: java CDToolParser <Infile> [Outfile]");
            System.out.println("if no outfile given, print to standard output");
            System.out.println("Author: Yussi Divnal");
        }
	public static void main(String args[]){
		if(args.length <1 || args.length >=3 || args[0]=="--help" || args[0]=="-h"){
			usage();
			System.exit(2);
		}
                String infilename = args[0];
		String outfilename= args[1];

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
        public static int TestFormat(String f)throws IOException{
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader((InputStream)fis);
            BufferedReader br = new BufferedReader(isr);
            String l="";
            while((l=br.readLine())!=null){
                if(l.startsWith("Generic"))return 0;
            }
            fis.close();
            return -1;
        }
}
