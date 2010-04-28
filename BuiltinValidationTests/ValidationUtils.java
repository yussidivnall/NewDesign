import java.io.File;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Element;
public class ValidationUtils{



	public static void appendResults(String filename,Document doc,Element elm){
	}

	public static void performSignalDeviationTest(String a[]){
		if(a.length < 4){
			System.out.println("Wrong arguments for this test!");
			System.exit(1);
		}else{
			String filename=a[0];
			String minwavelength=a[2];
			String deviation=a[3];
			Document doc = ParsingUtils.getXMLDocument(filename);
			int min = new Integer(minwavelength);
			double dev = new Double(deviation);
			try{
				UnsmoothDeviationFromZero test = new UnsmoothDeviationFromZero(doc,min,dev);
				appendResults(filename,doc,test.getTestResult());
			}catch(JDOMException je){
				je.printStackTrace();
			}
		} 
	}
	public static void performTest(String a[]){
		String testName = a[1];
		if(testName.equals("SignalDeviationFromZero")){
			performSignalDeviationTest(a);
		}else{
			System.out.println("Unknown test:"+testName);
		}
	}
	public static void usage(){
	}	
	public static void testArgs(String a[]){
		if(a.length < 2){
			System.out.println("Not enough arguments");
			usage();
			System.exit(1);
		}
		if(!new File(a[0]).exists()){
			System.out.println("No such file: "+a[0]);
			usage();
			System.exit(1);
		}
	}
	public static void main(String args[]){
		testArgs(args);
		performTest(args);
	}
}
