/**
 * 
 */
package nts.uk.shr.infra.file.constraint.stereotypes;

import java.util.ArrayList;
import java.util.List;

import nts.arc.layer.infra.file.constraint.FileStereo;

/**
 * @author hieult
 *
 */
public class FlowMenuStereoType implements FileStereo{

	
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
		return supportedExtensions;
	}

	@Override
	public List<String> getDownloadRights() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.file.constraint.FileStereo#getUploadRights()
	 */
	@Override
	public List<String> getUploadRights() {
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.file.constraint.FileStereo#getDeleteRights()
	 */
	@Override
	public List<String> getDeleteRights() {
		return new ArrayList<>();
	}

}
