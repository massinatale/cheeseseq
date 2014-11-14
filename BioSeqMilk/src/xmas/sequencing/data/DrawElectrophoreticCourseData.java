package xmas.sequencing.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class DrawElectrophoreticCourseData {

	private final DataSingleCourse dataFromXml;

	public DrawElectrophoreticCourseData(final DataSingleCourse dataFromXml) {
		super();
		this.dataFromXml = dataFromXml;
	}

	/**
	 * mette tutti i dati in un solo grafico..non normalizza ancora l'intensita'
	 */
	public void drawAllDataInAImage() {
		Set<String> keys = dataFromXml.getDataCollectionMap().keySet();
		Iterator<String> iter = keys.iterator();
		int numberOfSample = keys.size();
		int scalaFactor = (dataFromXml.getLengthOfElectrophoreticCourse() / 1000) + 1;
		while (iter.hasNext()) {
			String key = iter.next();
			if (dataFromXml.getDataCollectionMap().get(key).length > 1000) {
				Integer scaledData[] = new Integer[dataFromXml
						.getDataCollectionMap().get(key).length / scalaFactor];
				for (int i = 0; i < (dataFromXml.getDataCollectionMap()
						.get(key).length / scalaFactor); i++) {
					scaledData[i] = dataFromXml.getDataCollectionMap().get(key)[i
							* scalaFactor];
				}
				dataFromXml.addScaledDataMap(key, scaledData);
			} //
		} //
			// System.out.println("" + dataFromXml.getDataScaledMap().size());
		ImageProcessor processor = new ColorProcessor(1000, 700);
		processor.setColor(Color.white);
		processor.fill();
		// processor.setColor(Color.blue);
		Color colors[] = { Color.blue, Color.cyan, Color.green, Color.magenta };
		// /////////////////////////////
		Set<String> keysScaled = dataFromXml.getDataScaledMap().keySet();
		Iterator<String> iterScaled = keysScaled.iterator();
		int colorselector = 0;
		while (iterScaled.hasNext()) {
			processor.setColor(colors[colorselector]);
			colorselector++;
			String key = iterScaled.next();
			// System.out.println(key + " " +
			// dataFromXml.getDataScaledMap().get(key)[0]);
			for (int x = 0; x < dataFromXml.getDataScaledMap().get(key).length - 1; x++) {
				// System.out.println(x + " " +
				// dataFromXml.getDataScaledMap().get(key)[x]+ " " + (x + 1) +
				// " " + dataFromXml.getDataScaledMap().get(key)[x + 1]);
				processor.drawLine(x,
						600 - dataFromXml.getDataScaledMap().get(key)[x],
						(x + 1),
						600 - dataFromXml.getDataScaledMap().get(key)[x + 1]);
			}
		}
		ImagePlus img = new ImagePlus("test.png", processor);
		img.show();
	}

	public void drawPortionDataInAImage(int start, int end,
			String nameOfTheSample, int intensityThreashold, int tollerance) throws Exception {

		if ((end - start) > 1000) {
			throw new Exception("segli una porzione inferiore a 1000");
		}
		Set<String> keys = dataFromXml.getDataCollectionMap().keySet();
		Iterator<String> iter = keys.iterator();

		List<Integer[]> picks = findPicks(start, end, nameOfTheSample, intensityThreashold, tollerance);
		double scaleIntensity = 1.0;
		while (iter.hasNext()) {
			String key = iter.next();
			if (key.contains(nameOfTheSample)) {
				int maxOfIntensity = 0;
				for (int i = 0; i < (end - start); i++) {
					if (dataFromXml.getDataCollectionMap().get(key)[i + start] > maxOfIntensity) {
						maxOfIntensity = dataFromXml.getDataCollectionMap()
								.get(key)[i + start];
					}
				}
				scaleIntensity = 1.0;
				if (maxOfIntensity > 600) {
					scaleIntensity = 600.0 / (double) maxOfIntensity;
				}
				System.out.println("max:" + maxOfIntensity + " scale:"
						+ scaleIntensity);
				Integer scaledData[] = new Integer[end - start];
				for (int i = 0; i < (end - start); i++) {
					scaledData[i] = (int) (dataFromXml.getDataCollectionMap()
							.get(key)[i + start] * scaleIntensity);
				}
				dataFromXml.addScaledDataMap(key, scaledData);
			}
		} //
			// System.out.println("" + dataFromXml.getDataScaledMap().size());
		ImageProcessor processor = new ColorProcessor(1000, 700);
		processor.setColor(Color.white);
		processor.fill();
		// processor.setColor(Color.blue);
		processor.setColor(Color.black);
		processor.drawLine(0, 600, 1000, 600);
		Color colors[] = { Color.blue, Color.cyan, Color.green, Color.magenta };
		// /////////////////////////////
		Set<String> keysScaled = dataFromXml.getDataScaledMap().keySet();
		Iterator<String> iterScaled = keysScaled.iterator();
		int colorselector = 0;
		while (iterScaled.hasNext()) {
			processor.setColor(colors[colorselector]);
			colorselector++;
			String key = iterScaled.next();
			// System.out.println(key + " " +
			// dataFromXml.getDataScaledMap().get(key)[0]);
			for (int x = 0; x < ((end - start) - 1); x++) {
				// System.out.println(x + " " +
				// dataFromXml.getDataScaledMap().get(key)[x]+ " " + (x + 1) +
				// " " + dataFromXml.getDataScaledMap().get(key)[x + 1]);
				processor.drawLine(x,
						600 - dataFromXml.getDataScaledMap().get(key)[x],
						(x + 1),
						600 - dataFromXml.getDataScaledMap().get(key)[x + 1]);
			}
		}
		processor.setColor(Color.red);
		for (Integer[] p : picks){
			processor.drawLine(p[0] - start, 598, p[0] - start,  605);
			processor.drawLine(p[0] - start, 600 - (int)(p[1] * scaleIntensity) - 5, p[0] - start,  600 - (int)(p[1] * scaleIntensity));
		}
		
		ImagePlus img = new ImagePlus("test.png", processor);
		img.show();
	} // drawPortionDataInAImage

	public List<Integer[]> findPicks(int start, int end, String nameOfTheSample, int intensityThreashold, int tollerance) {
		Set<String> keys = dataFromXml.getDataCollectionMap().keySet();
		Iterator<String> iter = keys.iterator();
		
		List<Integer[]> picks = new ArrayList<>();
		while (iter.hasNext()) {
			String key = iter.next();
			if (key.contains(nameOfTheSample)) {
				for (int i = tollerance; i < (end - start) - tollerance; i++) {
					int intensityPrima = dataFromXml.getDataCollectionMap().get(key)[i + start - tollerance];
					int intensity = dataFromXml.getDataCollectionMap().get(key)[i + start];
					int intensityDopo = dataFromXml.getDataCollectionMap().get(key)[i + start +tollerance];
					if ((intensity > intensityThreashold) && (intensityPrima < intensity) && (intensityDopo < intensity)){
						Integer[] values = new Integer[2];
						values[0] = i + start;
						values[1] = intensity;
						picks.add(values);
					}
				}
			}
		} // 
		System.out.println(picks.size());
		return picks;
	}

}
