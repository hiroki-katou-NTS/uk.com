package nts.uk.shr.infra.file.constraint.stereotypes;

import java.util.ArrayList;
import java.util.List;

import nts.arc.layer.infra.file.constraint.FileStereo;

public class TitleBarStereoType implements FileStereo {

	@Override
	public long getLimitedSize() {
		return 5242880;
	}

	@Override
	public List<String> getSupportedExtension() {
		List<String> supportedExtensions = new ArrayList<String>();
		supportedExtensions.add("jpg");
		supportedExtensions.add("gif");
		supportedExtensions.add("bmp");
		supportedExtensions.add("jpeg");
		supportedExtensions.add("png");
		supportedExtensions.add("JPG");
		supportedExtensions.add("GIF");
		supportedExtensions.add("BMP");
		supportedExtensions.add("JPEG");
		supportedExtensions.add("PNG");
		return supportedExtensions;
	}

	@Override
	public List<String> getDownloadRights() {
		return new ArrayList<>();
	}

	@Override
	public List<String> getUploadRights() {
		return new ArrayList<>();
	}

	@Override
	public List<String> getDeleteRights() {
		return new ArrayList<>();
	}
}
