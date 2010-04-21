//package uc.validichro;
//import uc.validichro.datastructure;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;
import javax.xml.*;
import org.w3c.dom.*;
public class Loader{
	public static final String XPATH_DATE="/ValidichroData/ValidichroHeader/Date";
	public static final String XPATH_PDBID="/ValidichroData/ValidichroHeader/PDBID";




	public static void populateHeader(DataEntry de,Document doc,XPath xp){
		
	}

	public static void populate(DataEntry de,Document doc,XPath xp){
		populateHeader(de,doc,xp);
		String date;
	}
	public static DataEntry LoadXmlFile(String xmlfile) {
		DataEntry ret=new DataEntry();
		try{
			FileInputStream fis = new FileInputStream(xmlfile);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse((InputStream)fis);
			XPath xpath=XPathFactory.newInstance().newXPath();
			populate(ret,doc,xpath);

			XPathExpression expression=xpath.compile(XPATH_DATE);
			String Date = (String)expression.evaluate(doc,XPathConstants.STRING);
			ret.setDate(Date);
			System.out.println("Date:"+Date);
		}catch(Exception e){ //@ToDo: ParserConfigurationException,SAXException,FileNotFoundExceptio
			e.printStackTrace();
		}
		return ret;
		//return null;
	}
/*
*For testing only, this class should not be executed from shell
*/
	public static void main(String args[]){
		DataEntry ent = LoadXmlFile(args[0]);
		//LoadXmlFile("hjkljl;");
	}
}
