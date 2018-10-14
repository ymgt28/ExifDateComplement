package jpegExifRewriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;

import beans.DateGps;

public class JpegRewriterMain {

	public void JpegRewrite(Path pathI, Path pathO, DateGps dg) {
		File input = pathI.toFile();

		File output = pathO.toFile();

		RewriteGps rewrite = new RewriteGps(input,output);

		double lon = Double.parseDouble(dg.getLon());
		double lat = Double.parseDouble(dg.getLat());

		try {
			rewrite.doRewrite(lon, lat);
		} catch (ImageReadException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ImageWriteException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
