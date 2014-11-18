package xmas.sequencing.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import xmas.sequencing.data.DataSingleCourse;
import xmas.sequencing.data.DrawElectrophoreticCourseData;

public class XmlDataReaderSequenceFile {

	public DataSingleCourse dataUploadFormXml(final String path, final String fileName) throws Exception {

		DataSingleCourse dataFromXml = new DataSingleCourse();
		dataFromXml.setFileSource(fileName);

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(path.concat(fileName));

		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
		// System.out.println("# rootNode: " + rootNode.getName());
		List<Element> level1 = rootNode.getChildren();
		for (int r = 0; r < level1.size(); r++) {
			// System.out.println("## Level 1: " + level1.get(r).getName());
			List<Element> level2 = level1.get(r).getChildren();
			for (int f = 0; f < level2.size(); f++) {
				// System.out.println("### Level 2: "
				// + level2.get(f).getName());
				if (level2.get(f).getName().equals("Data_Source")) {
					dataFromXml.setDataSource(level2.get(f).getText());
					// System.out.println("          -> "
					// + level2.get(f).getText());
				}
				if (level2.get(f).getName().equals("Tag")) {
					List<Element> level3 = level2.get(f).getChildren();
					boolean isToSetContainerName = false;
					boolean isToCollectData = false;
					String keyData = new String();
					for (int l3 = 0; l3 < level3.size(); l3++) {
						if (level3.get(l3).getName().equals("Description")
								&& level3.get(l3).getText()
										.equals("Container Name")) {
							isToSetContainerName = true;
							// System.out.println("#### level3: "
							// + level3.get(l3).getName());
							// System.out.println("          -> "
							// + level3.get(l3).getChildText("Value"));
						} else if (level3.get(l3).getName().equals("Value")
								&& isToSetContainerName) {
							// System.out.println("          -> "
							// + level3.get(l3).getText());
							dataFromXml.setContainerName(level3.get(l3)
									.getText());
							isToSetContainerName = false;
						} else if (level3.get(l3).getName()
								.equals("Description")
								&& level3.get(l3).getText()
										.contains("Raw data for dye")) {
							isToCollectData = true;
							keyData = level3.get(l3).getText();
							// System.out.println("#### level3: "
							// + level3.get(l3).getName());
						} else if (level3.get(l3).getName().equals("Value")
								&& isToCollectData) {
							dataFromXml.addSampleToDataCollectionMap(keyData,
									level3.get(l3).getText());
							isToCollectData = false;
						}
					}

				}
				// Element field = level2.get(f);
			}
		}

		/*
		 * parte di codice solo di controllo
		 * 
		 * System.out.println(dataFromXml.getFileSource());
		 * System.out.println(dataFromXml.getDataSource());
		 * System.out.println(dataFromXml.getContainerName()); Set<String> keys
		 * = dataFromXml.getDataCollectionMap().keySet(); Iterator<String> iter
		 * = keys.iterator();
		 * 
		 * while(iter.hasNext()) { String key = iter.next(); Integer values[] =
		 * dataFromXml.getDataCollectionMap().get(key); System.out.println(key);
		 * System.out.println(values.length); for (int i : values){
		 * System.out.print(i + " "); } }
		 */

		if (!dataFromXml.checkDataQuality()) {
			throw new Exception(
					"i dati non hanno superato il controllo di qualita'");
		} else {
			System.out.println("upload dei dati avvenuto correttamente!");
		}
		return dataFromXml;

	} // dataUploadFormXml

	// ///////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////
	// main
	public static void main(String args[]) throws Exception {
		
		XmlDataReaderSequenceFile dataReaderSequenceFile = new XmlDataReaderSequenceFile();
		final String path = "/home/massimo/ProgettiCondivisi/cheeseseq/BioSeqMilk/data/";
		final String fileName = "BIE_DIC_TOM2_1.10_720_H05_2014-07-15_2.fsa.xml";
		DataSingleCourse dataFromXml = dataReaderSequenceFile
				.dataUploadFormXml(path, fileName);
		DrawElectrophoreticCourseData drawing = new DrawElectrophoreticCourseData(
				dataFromXml);
	} //
		// ///////////////////////////////////////////////////////////////

} // class
