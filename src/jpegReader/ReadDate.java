package jpegReader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

public class ReadDate {

	private File file;

	public ReadDate(File f) {
		file = f;
	}

	public String doRead() throws ImageReadException, IOException {

		final IImageMetadata metadata = Imaging.getMetadata(file);

		final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

		String ret = searchTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);

		return ret.substring(1, ret.length()-1);		//前後の'を省く

	}

	private String searchTagValue(final JpegImageMetadata jpegMetadata, final TagInfo tagInfo) {
		final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
		return field.getValueDescription();
	}

}
