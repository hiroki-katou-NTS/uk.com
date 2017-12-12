package nts.uk.shr.infra.file.constraint.stereotypes;

import java.util.ArrayList;
import java.util.List;

import nts.arc.layer.infra.file.constraint.FileStereo;

public class FlowMenuStereoType implements FileStereo {

	@Override
	public long getLimitedSize() {
		return 9000000;
	}

	@Override
	public List<String> getSupportedExtension() {
		return new ArrayList<>();
	}

	@Override
	public List<?> getDownloadRights() {
		return new ArrayList<>();
	}

	@Override
	public List<?> getUploadRights() {
		return new ArrayList<>();
	}

	@Override
	public List<?> getDeleteRights() {
		return new ArrayList<>();
	}

}
