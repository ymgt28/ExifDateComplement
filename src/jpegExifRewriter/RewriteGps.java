package jpegExifRewriter;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.apache.commons.imaging.util.IoUtils;

public class RewriteGps {

	private File input;
	private File output;

	public RewriteGps(File i, File o) {
		input = i;
		output = o;
	}

	public void doRewrite(double lon, double lat) throws ImageReadException, ImageWriteException, IOException {

		OutputStream os = null;
		boolean canThrow = false;
		try {
			TiffOutputSet outputSet = null;

			// note that metadata might be null if no metadata is found.
			final IImageMetadata metadata = Imaging.getMetadata(input);
			final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			if (null != jpegMetadata) {
				// note that exif might be null if no Exif metadata is found.
				final TiffImageMetadata exif = jpegMetadata.getExif();

				if (null != exif) {
					// TiffImageMetadata class is immutable (read-only).
					// TiffOutputSet class represents the Exif data to write.
					//
					// Usually, we want to update existing Exif metadata by
					// changing
					// the values of a few fields, or adding a field.
					// In these cases, it is easiest to use getOutputSet() to
					// start with a "copy" of the fields read from the image.
					outputSet = exif.getOutputSet();
				}
			}

			// if file does not contain any exif metadata, we create an empty
			// set of exif metadata. Otherwise, we keep all of the other
			// existing tags.
			if (null == outputSet) {
				outputSet = new TiffOutputSet();
			}

			outputSet.setGPSInDegrees(lon, lat);

			os = new FileOutputStream(output);
			os = new BufferedOutputStream(os);

			new ExifRewriter().updateExifMetadataLossless(input, os,
					outputSet);
			canThrow = true;
		} finally {
			IoUtils.closeQuietly(canThrow, os);
		}
	}
}
