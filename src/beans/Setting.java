package beans;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Setting implements Serializable {

	private String kmlFilePath;
	private String inputJpegPath;
	private String outputJpegPath;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;

	public String getKmlFilePath() {
		return kmlFilePath;
	}
	public String getInputJpegPath() {
		return inputJpegPath;
	}
	public String getOutputJpegPath() {
		return outputJpegPath;
	}
	public ZonedDateTime getStartDate() {
		return startDate;
	}
	public ZonedDateTime getEndDate() {
		return endDate;
	}
	public void setKmlFilePath(String kmlFilePath) {
		this.kmlFilePath = kmlFilePath;
	}
	public void setInputJpegPath(String inputJpegPath) {
		this.inputJpegPath = inputJpegPath;
	}
	public void setOutputJpegPath(String outputJpegPath) {
		this.outputJpegPath = outputJpegPath;
	}
	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(ZonedDateTime endDate) {
		this.endDate = endDate;
	}

}
