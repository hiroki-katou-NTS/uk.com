package nts.uk.shr.infra.file.storage.filetype.defines;

import nts.uk.shr.infra.file.storage.filetype.FileTypeDescription;

public class SamplePackFileType implements FileTypeDescription {

	@Override
	public String name() {
		return "samplepack";
	}

	@Override
	public boolean isPack() {
		return true;
	}
}
