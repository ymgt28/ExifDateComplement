/*
 *  単一JPEGファイルのExif情報から日付情報を得る
 * */
package jpegReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.imaging.ImageReadException;

public class JpegReaderMain {

	public ZonedDateTime jpegRead(Path path) {
		File file = path.toFile();

		ReadDate read = new ReadDate(file);
		String ret = "";
		try {
			ret = read.doRead();
		} catch (ImageReadException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		String input = ret + " JST";
		//System.out.println("Date -> " + input);
		ZonedDateTime retDate = ZonedDateTime.parse(input,
				DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss zzz"));

		return retDate;
	}

}
