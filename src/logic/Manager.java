package logic;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.List;

import beans.DateGps;
import beans.Setting;
import jpegExifRewriter.JpegRewriterMain;
import jpegReader.JpegReaderMain;
import kmlReader.KmlReaderMain;

public class Manager {

	private Setting setting;
	private List<DateGps> dateGpsList;

	public Manager(Setting s) {
		setting = s;
	}

	public void doComplement() throws IOException {
		kmlRead();
		System.out.println("KMLファイルの読み込み完了");

		jpegProgress();
	}

	private void kmlRead() {
		KmlReaderMain kmlR = null;
		if(setting.getStartDate() != null) {
			if(setting.getEndDate() != null) {
				kmlR = new KmlReaderMain(setting.getStartDate(), setting.getEndDate());
			}else {
				kmlR = new KmlReaderMain(setting.getStartDate());
			}
		}else {
			kmlR = new KmlReaderMain();
		}
		kmlR.read(setting.getKmlFilePath());
		dateGpsList = kmlR.getList();
	}

	private void jpegProgress() throws IOException {
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(setting.getInputJpegPath()), "*.{JPG,jpg,jpeg}");
		int size = 290;
		int count = 0;
		for(Path path : directoryStream) {
			count++;
			System.out.print("("+count + "/" + size + ") ");
			System.out.println(path.toFile().toString());
			jpegProgressSingle(path);
		}
	}

	private void jpegProgressSingle(Path inputPath) {
		JpegReaderMain jpegR = new JpegReaderMain();
		ZonedDateTime jpegDate = jpegR.jpegRead(inputPath);

		DateGps selectDg = null;
		for(int i=1; i<dateGpsList.size(); i++) {
			ZonedDateTime bef = dateGpsList.get(i-1).getTime();
			ZonedDateTime aft = dateGpsList.get(i).getTime();
			if(!(jpegDate.isBefore(aft) || jpegDate.isAfter(bef))) {	//"より後/前"なのでその時間を含まないため!(x||y)
				selectDg = dateGpsList.get(i);
				break;
			}
		}
		if(selectDg == null) {
			System.out.println("NULL");
			return;
		}
		String output = setting.getOutputJpegPath() + inputPath.toFile().getName();
		JpegRewriterMain jpegRW = new JpegRewriterMain();
		jpegRW.JpegRewrite(inputPath, Paths.get(output), selectDg);
	}

}
