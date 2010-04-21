//package uc.validichro.datastructure;
/*
*
Description:Holds a single entry of the Spectral table
*@ToDo implement all values of all rows in file


*/

import java.math.*;
public class SpectralEntry{
	public BigDecimal myWaveLength;
	public BigDecimal mySmooth;
	public BigDecimal myUnsmooth;
	SpectralEntry(BigDecimal wavelength,BigDecimal smooth,BigDecimal unsmooth){
		myWaveLength=wavelength;
		mySmooth=smooth;
		myUnsmooth=unsmooth;	
	}
}
