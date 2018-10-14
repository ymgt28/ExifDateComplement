package kmlReader;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import beans.DateGps;

public class SAXHandler extends DefaultHandler {

	private List<DateGps> dgList;
	private ZonedDateTime date;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private String line;

	public SAXHandler() {
		dgList = new ArrayList<DateGps>();
		startDate = null;
		endDate = null;
	}

	public SAXHandler(ZonedDateTime s, ZonedDateTime e) {
		this();
		startDate = s;
		endDate = e;
	}

	public List<DateGps> getDgList(){
		return dgList;
	}

	public void startDocument() throws SAXException {
		//System.out.println("*** startDocument ***");
	}

	public void endDocument() throws SAXException {
		//System.out.println("*** endDocument ***");
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		//タグ内に情報があるタイプはここで処理
	}

	public void endElement(String namespaceURI, String localName, String qName) {
		//終了タグを見て、line変数を処理
		if (qName.equals("when")) {
			date = ParseDate(line);
			if(isLangeOutDate(date)) {
				date = null;
			}
		}else if(qName.equals("gx:coord")) {		//ここでDateとGpsをListへ挿入
			if(date != null) {
				DateGps dg = new DateGps();
				ParseGps(dg, line);
				dg.setTime(date);
				InsertList(dg);
			}
		}

	}

	public void characters(char[] ch, int start, int length) {
		line = new String(ch, start, length);
	}

	private ZonedDateTime ParseDate(String text) {
		return ZonedDateTime.parse(text);
	}

	private void ParseGps(DateGps dg, String text) {
		String[] sp = text.split("[\\s]+",0);
		dg.setLon(sp[0]);
		dg.setLat(sp[1]);
		if(sp.length >= 3) {
			dg.setAlt(sp[2]);
		}
	}

	private void InsertList(DateGps dg) {	//直後の同じ場所の重複を省く
		if(dgList.size() == 0) {		//リストが空の場合
			dgList.add(dg);
			return;
		}
		DateGps last = dgList.get(dgList.size()-1);
		if((!last.getLon().equals(dg.getLon())) || (!last.getLat().equals(dg.getLat()))) {
			dgList.add(dg);
		}
	}

	private boolean isLangeOutDate(ZonedDateTime date) {
		if((startDate == null) && (endDate == null)) {
			return false;
		}
		if(date.isAfter(startDate) && date.isBefore(endDate)){
			return false;
		}
		return true;
	}

}
