This represents the data structure, each entry, genraly speaking is data extracted from a single circular dichrosis file,
The top class is DataEntry.
DataEntry
  |--------->Head-->String PDBID
  |		|-->String Date
  |		|-->String temprature
  |		|-->etc
  |
  |--------->Body-->SpectralData-->Enteries-->SpectralEntery
					|---->SpectralEntery
					|---->SpectralEntery
					|---->etc
