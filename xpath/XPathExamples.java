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
		while(itr.hasNext()){
			Element e = (Element)itr.next();
			String wl=e.getChild("WaveLength").getTextTrim();
			String sm=e.getChild("Smooth").getTextTrim();
			String us=e.getChild("unsmooth").getTextTrim();
			System.out.println(wl+"\t"+sm+"\t"+us);
		}
	}
	public void initDocument() throws IOException,JDOMException{
		FileInputStream fis = new FileInputStream(file);
		SAXBuilder builder = new SAXBuilder();
		doc = builder.build(fis);
		fis.close();
	}
	public List getList(String tags)throws JDOMException{
		String expression="//"+tags;
		XPath xpath= XPath.newInstance(expression);
		return xpath.selectNodes(doc);
	}
	public XPathExamples(){
		System.out.println("Starting");
		try{
			initDocument();
			printSomeWavelength();
			List AllWaveLengths = getList("WaveLength");
			List AllSmooth=getList("Smooth");
		}catch (IOException ioe){
			ioe.printStackTrace();
		}catch (JDOMException je){
			je.printStackTrace();
		}
	}
	public static void main(String args[]){
		XPathExamples me = new XPathExamples();
	}
}
