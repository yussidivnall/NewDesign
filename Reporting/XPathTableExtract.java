import java.io.File;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.xpath.XPath;
import org.jdom.JDOMException;
import java.util.List;
import java.util.Iterator;
public class XPathTableExtract{
	public static String deliminator="\t\t\t";
	public XPathTableExtract(Document doc,String args[]){
		//Note: First arg is filename, should fix main() and then change to for(i=0;...)
		try{
			XPath xpath = XPath.newInstance("//ValidichroBody/SpectralEntry");
			List list = xpath.selectNodes(doc);
			Iterator itr = list.iterator();
			//Print labels (tag names)
			for(int i=1;i<args.length;i++){
				System.out.print(args[i]+deliminator);
			}
			while(itr.hasNext()){
				Element e = (Element)itr.next();
				System.out.println(e.getText());
				for(int i=1;i<args.length;i++){
					String s=e.getChild(args[i]).getTextTrim();
					Float f = new Float(s);
					System.out.print(f+deliminator);
				}
			}
			System.out.println();
		}catch(JDOMException je){
			je.printStackTrace();
		}
	}


	public static void usage(){
		System.out.println("XPathTableExtract - Extracts columns from an XML file");
		System.out.println("Usage:");
		System.out.println("java XPathTableExtract <XML File> <Column tag name> [Column tag name 2] [Column tag name 3]...");
		System.out.println("java XPathTableExtract <Options>");
		System.out.println("Options:");
		System.out.println("	--help -h :print this message and quit");
                System.out.println("Note:");
                System.out.println("Requires jdom will be in the java classpath");
                System.out.println("or use java --classpath <Path To jdom.jar> XPathTableExtract...");
	}
	public static Document argsCheck(String a[]){
		if(a.length < 2){
			System.out.println("Not enough arguments");
			usage();
			System.exit(1);
		}
		if(!new File(a[0]).exists()){
			System.out.println("No such file:"+a[1]);
			usage();
			System.exit(1);
		}
		if(a[0]=="--help"||a[0]=="-h"){
			usage();
			System.exit(0);
		}
		Document ret = ParsingUtils.getXMLDocument(a[0]);
		if(ret==null){
			System.out.println("Not a valid XML file:"+a[0]);
			usage();
			System.exit(1);
		}
		return ret;
	}
	public static void main(String args[]){
		Document d=argsCheck(args);
		XPathTableExtract me = new XPathTableExtract(d,args);
		
	}
}
