package xmas.sequencing.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DataSingleCourse {

	private String fileSource;
	private String dataSource;
	private String containerName;
	private HashMap<String, Integer[]> dataCollectionMap = new HashMap<String, Integer[]>();
	private HashMap<String, Integer[]> dataScaledMap = new HashMap<String, Integer[]>();
	private int lengthOfElectrophoreticCourse = 0;

	// private boolean quickControl;

	public String getFileSource() {
		return fileSource;
	} // getFileSource

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	} // setFileSource

	public String getDataSource() {
		return dataSource;
	} // getDataSource

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	} // setDataSource

	public String getContainerName() {
		return containerName;
	} // getContainerName

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	} // setContainerName

	public HashMap<String, Integer[]> getDataCollectionMap() {
		return dataCollectionMap;
	} // getDataCollectionMap

	public void setDataCollectionMap(
			HashMap<String, Integer[]> dataCollectionMap) {
		this.dataCollectionMap = dataCollectionMap;
	} // setDataCollectionMap

	public void addSampleToDataCollectionMap(String key, String dataLine) {
		String[] numberStrs = dataLine.split(" ");
		Integer[] numbers = new Integer[numberStrs.length];
		for (int i = 0; i < numberStrs.length; i++) {
			numbers[i] = Integer.parseInt(numberStrs[i]);
		}
		this.dataCollectionMap.put(key, numbers);
	} // addSampleToDataCollectionMap
	
	public void addScaledDataMap(String key, Integer[] scaledData){
		this.dataScaledMap.put(key, scaledData);
	} // addScaledDataMap
	
	public HashMap<String, Integer[]> getDataScaledMap() {
		return dataScaledMap;
	} // getDataScaledMap

	public int getLengthOfElectrophoreticCourse() {
		haveElectrophoreticCoursesSameLength();
		return lengthOfElectrophoreticCourse;
	}

	public boolean checkDataQuality() {
		return ((!fileSource.isEmpty()) && (!dataSource.isEmpty())
				&& (!containerName.isEmpty()) && (!dataCollectionMap.isEmpty())
				&& (haveElectrophoreticCoursesSameLength()));
		/* da aggiungere il controllo sulla lunghezza dei dati */
	} // dataCollectionMap
	

	public boolean haveElectrophoreticCoursesSameLength() {
		Set<String> keys = this.dataCollectionMap.keySet();
		Iterator<String> iter = keys.iterator();
		boolean correctLength = true;
		while (iter.hasNext()) {
			String key = iter.next();
			if (lengthOfElectrophoreticCourse == 0) {
				lengthOfElectrophoreticCourse = this.dataCollectionMap.get(key).length;
			} else {
				if (lengthOfElectrophoreticCourse != this.dataCollectionMap
						.get(key).length)
					correctLength = false;
			}
		}
		return correctLength;
	} // haveElectrophoreticCoursesSameLength
} // class
