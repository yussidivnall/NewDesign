public class config{
public static String ValidationFiles="/home/volcan/Desktop/development/BensProject/gen_files/";
public static String XMLFiles="/home/volcan/Desktop/development/BensProject/xmls/";
public static String ReportFiles="/home/volcan/Desktop/development/BensProject/reports/";
public static String DefinitionFiles="/home/volcan/Desktop/development/BensProject/NewDesign/Definitions/";


public static void main(String args[]){
	if(args.length==0)return;
	if(args[0].equals("getXMLPath")){
		System.out.println(XMLFiles);
	}
	if(args[0].equals("getReportsPath")){
		System.out.println(XMLFiles);
	}
	if(args[0].equals("getInputPath")){
		System.out.println(ValidationFiles);
	}
}


}
