package beans;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class DateGps implements Serializable {

	private String lon;		//軽度 longitude ≥ −180 and <= 180
	private String lat;		//緯度 latitude ≥ −90 and ≤ 90
	private String alt;		//高度 altitude values (optional) are in meters above sea level
	private ZonedDateTime time;		//タイムゾーン付き日時

	public ZonedDateTime getTime() {
		return time;
	}
	public void setTime(ZonedDateTime time) {
		this.time = time;
	}
	public String getLon() {
		return lon;
	}
	public String getLat() {
		return lat;
	}
	public String getAlt() {
		return alt;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}

}
