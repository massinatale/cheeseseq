package xmas.imagej;

import xmas.sequencing.data.DrawElectrophoreticCourseData;
import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class DrawCurveFromRawData {

	public static void main(String args[]) {
		ImageProcessor processor = new ColorProcessor(600, 400);
		ImagePlus img = new ImagePlus("test.png", processor);
		img.show();

	}

}
