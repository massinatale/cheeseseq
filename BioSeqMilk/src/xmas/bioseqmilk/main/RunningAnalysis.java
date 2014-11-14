package xmas.bioseqmilk.main;

import xmas.sequencing.data.DataSingleCourse;
import xmas.sequencing.data.DrawElectrophoreticCourseData;
import xmas.sequencing.xml.XmlDataReaderSequenceFile;

public class RunningAnalysis {
	
	//3000 e 4000 nell'immage ID2

	// ///////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////
	// main
	public static void main(String args[]) throws Exception {
		XmlDataReaderSequenceFile dataReaderSequenceFile = new XmlDataReaderSequenceFile();
		DataSingleCourse dataFromXml = dataReaderSequenceFile
				.dataUploadFormXml();
		DrawElectrophoreticCourseData drawing = new DrawElectrophoreticCourseData(
				dataFromXml);
		//drawing.drawAllDataInAImage();
		drawing.drawPortionDataInAImage(3000, 3999, "2", 50, 2);
	} //
		// ///////////////////////////////////////////////////////////////
}
