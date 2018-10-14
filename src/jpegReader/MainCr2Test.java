package jpegReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.imaging.ImageReadException;

public class MainCr2Test {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Path path = Paths.get("/Users/yamagata/Desktop/test/test5.CR2");
		File file = path.toFile();
		MetadataExample ex = new MetadataExample();
		try {
			ex.metadataExample(file);
		} catch (ImageReadException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
