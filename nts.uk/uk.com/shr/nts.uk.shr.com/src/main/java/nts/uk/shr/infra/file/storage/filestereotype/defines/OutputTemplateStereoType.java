package nts.uk.shr.infra.file.storage.filestereotype.defines;

import nts.uk.shr.infra.file.storage.filestereotype.FileStereoTypeDescription;

import java.util.Arrays;
import java.util.List;

public class OutputTemplateStereoType implements FileStereoTypeDescription{

	@Override
	public String name() {
		return "output_template";
	}

	@Override
	public List<String> acceptableExtensions() {
		return Arrays.asList("xlsx");
	}
}
