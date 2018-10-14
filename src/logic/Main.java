/*
 * kmlから日付とGPSを読み込み、
 * 対応する日付のJPEGファイルにGPSのExifを埋め込む
 * */
package logic;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import beans.Setting;

public class Main {

	public static void main(String args[]) {
		String kmlFilePath = "/Users/yamagata/Downloads/Takeout/loc/loc.kml";
		String inputJpegPath = "/Volumes/SeagateExpansionDrive/backup/2018-07-14/";
		String outputJpegPath = "/Volumes/SeagateExpansionDrive/backup/2018-07-14/exif/";
		ZonedDateTime startDate = ZonedDateTime.of(2018, 7, 10, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
		ZonedDateTime endDate = ZonedDateTime.of(2018, 7, 20, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));

		Setting setting = new Setting();
		setting.setKmlFilePath(kmlFilePath);
		setting.setInputJpegPath(inputJpegPath);
		setting.setOutputJpegPath(outputJpegPath);
		setting.setStartDate(startDate);
		setting.setEndDate(endDate);

		Manager mng = new Manager(setting);
		try {
			mng.doComplement();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
