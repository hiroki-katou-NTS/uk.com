package nts.uk.shr.infra.file.storage.filestereotype;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import nts.uk.shr.infra.file.storage.filestereotype.defines.*;

final class FileStereoTypeDef {
	
	private static Map<String, FileStereoTypeDescription> map = new HashMap<>();
	static {
		Arrays.asList(
				// Add file type descriptions here
				new SampleFileStereoType(),
				new SamplePackStereoType(),
				new FlowMenuStereoType(), 
				new AvatarFileStereoType(),
				new CsvFileStereoType(),
				new DocumentFileStereoType(),
				new TemporaryZipStereoType(),
				new ExcelFileStereoType(),
				new FlowmenuDocumentStereoType(),
				new FlowmenuIconStereoType(),
				new FlowmenuMakeStereoType(),
				new OutputTemplateStereoType()
		).stream().forEach(d -> {
			map.put(d.name(), d);
		});
	}

	public static Optional<FileStereoTypeDescription> of(String nameOfFileType) {
		return Optional.ofNullable(map.get(nameOfFileType));
	}
}
