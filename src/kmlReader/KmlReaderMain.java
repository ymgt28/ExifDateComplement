/*
 * KML -> 日付とGPS情報のbeanに変換をテスト
 * */

package kmlReader;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import beans.DateGps;

public class KmlReaderMain {

	private String filePath;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private ZonedDateTime nowZonedDt;
	private List<DateGps> list;

	public KmlReaderMain() {
		startDate = null;
		endDate = null;
		nowZonedDt = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
		list = null;
	}

	public KmlReaderMain(ZonedDateTime s) {
		this();
		startDate = s;
		endDate = nowZonedDt;
	}

	public KmlReaderMain(ZonedDateTime s, ZonedDateTime e) {
		this();
		startDate = s;
		endDate = e;
	}

	public List<DateGps> getList(){
		return list;
	}

	public void read(String path) {
		filePath = path;

		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			SAXParser saxParser = spf.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			SAXHandler saxh;
			if(startDate == null && endDate == null) {
				saxh = new SAXHandler();
			}else {
				saxh = new SAXHandler(startDate,endDate);
			}
			xmlReader.setContentHandler(saxh);
			xmlReader.parse("file:" + filePath);
			list = saxh.getDgList();

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println("size:" + list.size());
		/*for(DateGps d : list) {
			System.out.print("Date:"+d.getTime());
			System.out.print(" Lon:"+d.getLon());
			System.out.print(" Lat:"+d.getLat());
			System.out.println(" Alt:"+d.getAlt());
		}*/
		//System.out.println("Lon:" + dg.getLon());
		//System.out.println("Lat:" + dg.getLat());


	}

}
