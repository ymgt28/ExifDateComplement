package cr2ExifRewriter;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.write.TiffImageWriterLossless;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

public class Cr2RewriterMain {

	public static void main(String[] args) throws ImageWriteException, IOException, ImageReadException {
		// TODO 自動生成されたメソッド・スタブ

		File f = new File("/Users/yamagata/Desktop/test/test5.CR2");
		File dst = new File("/Users/yamagata/Desktop/test/test555.CR2");

		BufferedImage img = Imaging.getBufferedImage(f, new HashMap<>());
		byte[] imageBytes = Imaging.writeImageToBytes(img, ImageFormats.TIFF, new HashMap<>());

		TiffOutputSet outputSet = null;

		final IImageMetadata metadata = Imaging.getMetadata(imageBytes);
	    final TiffImageMetadata tiffMetadata = (TiffImageMetadata) metadata;
	    outputSet = tiffMetadata.getOutputSet();

	    if (null == outputSet) {
	        outputSet = new TiffOutputSet();
	    }

	    final double longitude = -74.0;
	    final double latitude = 40 + 43 / 60.0;
	    outputSet.setGPSInDegrees(longitude, latitude);

		try(FileOutputStream fos = new FileOutputStream(dst);
		    OutputStream os = new BufferedOutputStream(fos)) {
		    new TiffImageWriterLossless(imageBytes).write(os, outputSet);
		}
	}

}
