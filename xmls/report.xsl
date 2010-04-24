<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html"/>
  <xsl:param name="image_dir" select="'images'"/>
  <xsl:template match="/">
    <html>
      <body>
	<h1><xsl:value-of select="//ProteinType"/></h1>
	<table border="1">
		<tr>
			<th>Wavelength</th>
			<th>Signal</th>
			<th>HighTension</th>
			<th>SmoothSignal</th>
			<th>PseudoAbsorbance</th>
			<th>SampleStandardDeviation</th>
			<th>BaselineStandardDeviation</th>
		</tr>
			<xsl:for-each select="//SpectralEntry"> 
				<tr>
					<td><xsl:value-of select="Wavelength"/></td>
					<td><xsl:value-of select="Signal"/></td>
					<td><xsl:value-of select="HighTension"/></td>
					<td><xsl:value-of select="SmoothSignal"/></td>
 					<td><xsl:value-of select="PseudoAbsorbance"/></td>
					<td><xsl:value-of select="SampleStandardDeviation"/></td>
					<td><xsl:value-of select="BaselineStandardDeviation"/></td>
				</tr>
			</xsl:for-each>
		
	</table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
