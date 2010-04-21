//package uc.validichro;

/*
*Represents data harvested from a single file
*Header - Contains the header data (date, temprature pdbid etc)
*Body - Contains SpectralData
*/

public class DataEntry{
	DataHeader Header;
	DataBody Body;
	
	DataEntry(){
		Header = new DataHeader();
		Body = new DataBody();
	}
	DataEntry(DataHeader header,DataBody body){
		Header = header;
		Body = body;
	}

	/*
	*	calls to header
	*
	*/

	public void setPDBID(String s){Header.setPDBID(s);}
	public String getPDBID(){return Header.getPDBID();}

        public void setDate(String d){Header.setDate(d);}
        public String getDate(){return Header.getDate();}








}
