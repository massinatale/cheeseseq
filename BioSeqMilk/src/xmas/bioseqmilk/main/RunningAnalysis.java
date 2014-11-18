package xmas.bioseqmilk.main;

import xmas.sequencing.data.DataSingleCourse;
import xmas.sequencing.data.DrawElectrophoreticCourseData;
import xmas.sequencing.xml.XmlDataReaderSequenceFile;

public class RunningAnalysis {
	

	/* il path è da cambiare a seconda della cartella */
	final static String path = "/Users/massimonatale/Documents/Progetti/cheeseseq/BioSeqMilk/data/";
	/* nome del file */
	final static String fileName = "BIE_DIC_TOM2_1.10_720_H05_2014-07-15_2.fsa.xml";
	/* valore in di inizio del range da controllare */
	final static int startValue = 3000;
	/* valore del fine del range da controllare */
	final static int endValue = 4000;
	/* fluoroforo usato */
	final static String dye = "2";
	/* intensità minima dei picchi da rilevare */
	final static int intensityThreashold = 50;
	/* scostamento dei picchi dal vertice */
	final static int tollerance = 2;
	// ///////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////
	// main
	public static void main(String args[]) throws Exception {
		XmlDataReaderSequenceFile dataReaderSequenceFile = new XmlDataReaderSequenceFile();
		DataSingleCourse dataFromXml = dataReaderSequenceFile
				.dataUploadFormXml(path, fileName);
		DrawElectrophoreticCourseData drawing = new DrawElectrophoreticCourseData(
				dataFromXml);
		//drawing.drawAllDataInAImage();
		drawing.drawPortionDataInAImage(startValue, endValue, dye, intensityThreashold, tollerance);
	} //
		// ///////////////////////////////////////////////////////////////
}
