//package uc.validichro.datastructure;
import java.math.*;
import java.util.*;
//Hold a table of Spectral Entries
public class SpectralData{
	ArrayList<SpectralEntry> Enteries;
	SpectralData(){
		Enteries=new ArrayList();
	}
	public void addEntry(BigDecimal wavelength,BigDecimal smooth,BigDecimal unsmooth){
		Enteries.add(new SpectralEntry(wavelength,smooth,unsmooth));	
	}
}
