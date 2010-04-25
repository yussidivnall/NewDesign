import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;
import org.jdom.JDOMException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
//These imports are only used for static statup (from main)
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;
import java.io.*;
public class UnsmoothDeviationFromZero extends ValidationTest{
	String TestName="UnsmoothDeviationFromZero";
	int AboveWavelength;
	double deviationLimit;

	List DeviatingElements;
	Document myDocument;
	Element result;

	public Element getTestResult(){
		Element ret = new Element(Parser.TEST_TAG);
		ret.setAttribute("name",TestName);
		ret.setAttribute("min_wavelength",""+AboveWavelength);
		ret.setAttribute("limit",""+deviationLimit);
		Element res=new Element(Parser.TEST_RESULT_TAG);
		if(DeviatingElements.size()==0){
			res.setAttribute(Parser.TEST_STATUS,Parser.TEST_STATUS_ACCEPTABLE);
			ret.addContent(res);
		}else{
			res.setAttribute(Parser.TEST_STATUS,Parser.TEST_STATUS_FLAGGED);
			Iterator iter=DeviatingElements.iterator();
			while(iter.hasNext()){
				Element elm=(Element)iter.next();
				res.addContent((Element)elm.clone());
			}
			ret.addContent(res);
		}
		return ret;	
	}

	UnsmoothDeviationFromZero(Document d,int aboveWL,double deviationFromZero)throws JDOMException{
		myDocument =d;
		AboveWavelength=aboveWL; deviationLimit=deviationFromZero;
		String wavelength_tag=Parser.WAVELENGTH_TAG;
		String unsmooth_tag=Parser.SIGNAL_TAG;
		// "//SpectralEntry[Unsmooth > 0.9 or Unsmooth < -0.9 and Wavelength > 260]"
		String cond="("+unsmooth_tag+" > "+deviationFromZero+" or "+unsmooth_tag+" < "+(-deviationFromZero)+") and "+wavelength_tag+" >"+aboveWL;
		String e="//SpectralEntry["+cond+"]";
		System.out.println("\n\nXPath Expression:"+e+"\n\n");
		DeviatingElements = (List)XPath.newInstance(e).selectNodes(d);
	}


/*
*
*	Mostly for testing
*
*/
	public static void dump(Element e) throws IOException{
		XMLOutputter xo=new XMLOutputter();
		xo.output(e,System.out);
		System.out.println();
	}
//Take 3 arguments: xmlfile(String), minimum wavelength(int),deviation from zero(double)
	public static void main(String args[]){
		String filename=args[0];
		int min_wl=new Integer(args[1]);
		double devi=new Double(args[2]);
		try{
			FileInputStream fis = new FileInputStream(filename);
			Document doc = new SAXBuilder().build(fis);
			fis.close();
			UnsmoothDeviationFromZero me = new UnsmoothDeviationFromZero(doc,min_wl,devi);
			Element res=me.getTestResult();
			dump(res);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
