package nts.uk.shr.infra.file.storage.filetype.defines;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nts.uk.shr.infra.file.storage.filetype.FileTypeDescription;

@Getter
public class SampleFileType implements FileTypeDescription {

	@Override
	public String name() {
		return "samplefile";
	}

	@Override
	public List<String> acceptableExtensions() {
		return Arrays.asList("png", "jpg", "jpeg", "gif");
	}
}
