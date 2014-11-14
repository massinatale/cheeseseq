package xmas.sequencing.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlDataReaderCogito {

	public static void main(String args[]) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(
				"/home/massimo/Documenti/POC/ExpertSystem/OutputExample1.xml");

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			Element extraction = rootNode.getChild("EXTRACTION");
			List<Element> recordS = extraction.getChildren();
			for (int r = 0; r < recordS.size(); r++){
				List<Element> filedS = recordS.get(r).getChildren();
				for (int f = 0; f < filedS.size(); f++){
					Element field = filedS.get(f);
					System.out.println("#Token:" + field.getAttributeValue("BASE") + " #Sentiment:" +field.getAttributeValue("NAME").toLowerCase());
				}
			}
			
			Element synthesis = rootNode.getChild("SYNTHESIS");
			Element relevants = synthesis.getChild("RELEVANTS");
			List<Element> relevantList = relevants.getChildren();
			System.out.println(relevantList.size());

			for (int i = 0; i < relevantList.size(); i++) {
				Element node = (Element) relevantList.get(i);
				System.out.println("#Name:" + node.getAttributeValue("NAME")
						+ " #Score:" + node.getAttributeValue("SCORE"));
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

	} // main

} // class
