//http://notetodogself.blogspot.com/2007/11/parse-xml-with-jdom-and-xpath.html
import java.io.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import java.util.*;
public class XPathExamples{
	/* Some Example XPath Expressions
	String AllWaveLength="//WaveLength";
	String AllEntriesBiggerThen="//SpectralEntry[WaveLength>180.0]";
	String AllEntriesBetween="//SpectralEntry[WaveLength>180.0 and WaveLength < 200]";
	String AllEntriesOutsideLimit="//SpectralEntry[WaveLength > //HighWavelength  or  WaveLength < //LowWaveLength]"
	*/
	String file="./a2500.gen.valixml"; // Some vali xml file
	Document doc;
	public void printSomeWavelength()throws IOException,JDOMException{
		//FileInputStream fis = new FileInputStream(file);		
		//SAXBuilder builder = new SAXBuilder();
		//Document doc = builder.build(fis);
		//fis.close();
		XPath xpath = XPath.newInstance("//SpectralEntry[WaveLength < //HighWavelength  or  WaveLength > //LowWaveLength]");
		List list = xpath.selectNodes(doc);
		Iterator itr = list.iterator();
		System.out.println("Wavelength\tSmooth\tUnsmooth\tU1\tU2\tU3\tU4");
		while(itr.hasNext()){
			Element e = (Element)itr.next();
			
			String wl=e.getChild("WaveLength").getTextTrim();
			String sm=e.getChild("Smooth").getTextTrim();
			String us=e.getChild("unsmooth").getTextTrim();
                        String u1=e.getChild("Unknown1").getTextTrim();
                        String u2=e.getChild("Unknown2").getTextTrim();
                        String u3=e.getChild("Unknown3").getTextTrim();
                        String u4=e.getChild("Unknown4").getTextTrim();
			System.out.println(wl+"\t"+sm+"\t"+us+"\t"+u1+"\t"+u2+"\t"+u3+"\t"+u4);
		}
	}
	public void initDocument() throws IOException,JDOMException{
		FileInputStream fis = new FileInputStream(file);
		SAXBuilder builder = new SAXBuilder();
		doc = builder.build(fis);
		fis.close();
	}
	public XPathExamples(String f){
		file=f;
		try{
			initDocument();
			printSomeWavelength();
		}catch (IOException ioe){
			ioe.printStackTrace();
		}catch (JDOMException je){
			je.printStackTrace();
		}
	}
	public static void main(String args[]){
		XPathExamples me = new XPathExamples(args[0]);	
	}
}
