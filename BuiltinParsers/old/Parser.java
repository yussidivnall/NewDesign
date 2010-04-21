package uc.validichro.parsers
class Parser{
	public static String getFileHeader(){
		return "<?XML?>\n";
	}

	public static String Tag(String label,String value){
		return StartingTag(label)+value+EndingTag(label);
	}
	public static String StartingTag(String name){
		return "<"+name+">";
	}
	public static String EndingTag(String name){
		return "</"+name+">";
	}
	public static String getTableRow(String in,String labels[]){
		return getTableRow(in,labels,"\\s+");
	}
	public static String getTableRow(String in,String labels[],String seperator){
		String columns[] = in.split(seperator);
		if(columns.length != labels.length){
			System.err.println("Given labels and actual number of columns don't match");
			System.err.println("Got "+labels.length+" labels, and found "+ columns.length+"columns ");
		}
		String ret="";
		for(int i=0;i<labels.length;i++){
			ret=ret+Tag(labels[i],columns[i]);
		}
		return ret;
		
	}
}
