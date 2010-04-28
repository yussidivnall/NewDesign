import java.util.*;
import java.math.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;
import org.jdom.JDOMException;
public class ValidationTest{
	//Adds test result (element) to Document(doc)
	public static void addTestResultToDocument(Document doc,Element element) throws JDOMException{
		String e="//"+Parser.VALIDATION_TESTS_TAG;
		List TestsTag = XPath.newInstance(e).selectNodes(doc);
		if(TestsTag.size()==0){ // Document doesn't yet have VALIDATION_TESTS_TAG (this is the first test performed on this document)
			System.out.println("No Tests tag yet, creating!");
			Element testsRoot = new Element(Parser.VALIDATION_TESTS_TAG);
			ParsingUtils.addToDocument(doc,"/"+Parser.DATA_TAG,testsRoot);
			TestsTag = XPath.newInstance(e).selectNodes(doc); // Selects again
		}
		//@ToDo : needs to check that this test with the same values have not already been performed
		ParsingUtils.addToDocument(doc,"/"+Parser.DATA_TAG+"/"+Parser.VALIDATION_TESTS_TAG,element);
		
	}
}
