/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author volcan
 */
public class Parser{
        public static final String DATA_TAG="ValidichroData"; // Top Tag for file's contents
            public static final String HEADER_TAG="ValidichroHeader";
                public static final String DATE_TAG="Date";
                public static final String GENERIC_TAG="Generic";
                public static final String LOW_WAVELENGTH_TAG="LowWavelength";
//                public static final String INTERVAL_NM_TAG="Interval_nm";
//                public static final String CUTOFF_NM_TAG="Cutoff_nm";
                public static final String MACHINE_TAG="Machine";
//                public static final String DWELLTIME_S_TAG="DwellTime_s";
                public static final String CELL_ID_TAG="Cell_id";
//                public static final String PATHLENGTH_CM_TAG="Pathlength_cm";
                public static final String PROTEIN_NAME_TAG="ProteinType";
                public static final String CODE_TAG="Code";
                public static final String HIGH_WAVELENGTH_TAG="HighWavelength";
                public static final String SMOOTHING_WINDOW_TAG="SmoothingWindow";
                public static final String TEMPERATURE_TAG="Temperature";
                public static final String PDB_TAG="PDB";
                public static final String CALIBRATION_FILE_TAG="CalibrationFile";
                public static final String UNITS_TAG="Units";
                public static final String ZEROED_BETWEEN_TAG="ZeroedBetween";
                public static final String BASELINE_TAG="BaseLine";
                public static final String SAMPLES_TAG="Samples";
                public static final String DESCRIPTION_TAG="Description";

                public static final String INTERVAL_TAG="Interval";
                public static final String CUTOFF_TAG="Cutoff";
                public static final String DWELLTIME_TAG="DwellTime";
                public static final String PATHLENGTH_TAG="PathLength";
                public static final String CONCENTRATION_TAG="Consentration";
                public static final String MRW_TAG="MRW";
           public static final String BODY_TAG="ValidictorBody";
                public static final String ENTRY_TAG="SpectralEntry";
                    public static final String WAVELENGTH_TAG="WaveLength";
                    public static final String SMOOTH_TAG="Smooth";
                    public static final String UNSMOOTH_TAG="Unsmooth";
                    /*
                     * Need to ask Ben what the rest of these represent!
                     */
                    public static final String VAL1="Unknown1";
                    public static final String VAL2="Unknown2";
                    public static final String VAL3="Unknown3";
                    public static final String VAL4="Unknown4";
                public static final String UNPARSED_TAG="Unparsed";
	public static String getFileHeader(){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	}

	public static String Tag(String label,String value){
		return StartingTag(label)+value+EndingTag(label);
	}
	public static String StartingTag(String name){
		return "<"+name+">";
	}
	public static String EndingTag(String name){
		return "</"+name+">";
	}
	public static String getTableRow(String in,String labels[]){
		return getTableRow(in,labels,"\\s+");
	}
	public static String getTableRow(String in,String labels[],String seperator){
		String columns[] = in.split(seperator);
		if(columns.length != labels.length){
			System.err.println("Given labels and actual number of columns don't match");
			System.err.println("Got "+labels.length+" labels, and found "+ columns.length+"columns ");
		}
		String ret="";
		for(int i=0;i<labels.length;i++){
			ret=ret+Tag(labels[i],columns[i]);
		}
		return ret;

	}
        public static int TestVDML(String f){
            //Should probably do more then this to check
            if(f.endsWith("vdml")){
                return 0;
            }else{
                return -1;
            }
        }
}
