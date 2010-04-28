import org.jdom.Element;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;
import org.jdom.xpath.XPath;
import org.jdom.JDOMException;
import java.io.*;
import java.util.List;
import java.util.Iterator;

public class ParsingUtils{

	//Adds "element" to all nodes captured in "root" xpath expression in "doc"
	public static void addToDocument(Document doc,String root,Element element){
		try{
			XPath xpath = XPath.newInstance(root);
			List list = xpath.selectNodes(doc);
			Iterator itr = list.iterator();
			while(itr.hasNext()){
				Element parent= (Element)itr.next();
				parent.addContent(element);
			}
		}catch(JDOMException je){
			je.printStackTrace();
		}
	}
	//Gets a Document from a given filename
	public static Document getXMLDocument(String filename){
		Document ret;
		try{
                        FileInputStream fis = new FileInputStream(filename);
                        ret = new SAXBuilder().build(fis);
                        fis.close();
			return ret;
                }catch(Exception e){
                        e.printStackTrace();
                }
		return null;
	}
	public static void writeXML(Document d,String filename){
		try{	
			PrintStream out = new PrintStream(new File(filename));
			XMLOutputter outputter = new XMLOutputter();
			outputter.setFormat(Format.getPrettyFormat());
			outputter.output(d,out);
			out.close(); 
		}catch(IOException ioe){ioe.printStackTrace();}
	}
//<Commend> <Args>
//Commands:
//	Append:
//		java... ParsingUtils append <File name> <Root node> <Input xml tag:(Element's text)>
//argv[0] command
//
	public static void main(String args[]){
		String cmd=args[0];
		if(cmd=="append"){
			String filename=args[1];
			String rootnode=args[2];
			Document d;
		}
	}

}
