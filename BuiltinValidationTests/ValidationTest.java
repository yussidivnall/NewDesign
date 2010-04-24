//Warning: RUBBISH CODE!!!!
import java.util.*;
import java.math.*;

import org.jdom.Document;
public class ValidationTest{
	//Two issues here,
	//First, would only return the y value, not the x,
	//Second, if the local max/min is equal to the left/right then it will fail to find it 
	//Get peaks

	


	public static List getLocalMaxima(List l){
		List<BigDecimal> ret = new ArrayList<BigDecimal>();
		for(int i=1;i<l.size()-1;i++){
			BigDecimal left_val=(BigDecimal)l.get(i-1);
			BigDecimal right_val=(BigDecimal)l.get(i+1);
			BigDecimal val=(BigDecimal)l.get(i);
			if(left_val.doubleValue()<val.doubleValue() && right_val.doubleValue()<val.doubleValue()){
				ret.add((BigDecimal)val);
			}
		}
		return ret;
	}
	//Get thoughs
	public static List getLocalMinima(List l){
                List<BigDecimal> ret = new ArrayList<BigDecimal>();
                for(int i=1;i<l.size()-1;i++){
                        BigDecimal left_val=(BigDecimal)l.get(i-1);
                        BigDecimal right_val=(BigDecimal)l.get(i+1);
                        BigDecimal val=(BigDecimal)l.get(i);
                        if(left_val.doubleValue()>val.doubleValue() && right_val.doubleValue()>val.doubleValue()){
                                ret.add((BigDecimal)val);
                        }
                }
                return ret;
        }
	public static List getRange(Document d,String field,double min,double max){
		ArrayList ret;
		//XPath xpath = XPath.newInstance("//"+field/);
		return null;
	}

}
