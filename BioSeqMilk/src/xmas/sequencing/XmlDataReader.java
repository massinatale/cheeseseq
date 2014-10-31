package xmas.sequencing;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlDataReader {
	
	public static void main (String args[]) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new File(args[0]));
		Element rootElement = document.getRootElement();

	} // main

} // class
