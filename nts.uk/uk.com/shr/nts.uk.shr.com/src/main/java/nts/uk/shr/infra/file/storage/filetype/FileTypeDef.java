package nts.uk.shr.infra.file.storage.filetype;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import nts.uk.shr.infra.file.storage.filetype.defines.SampleFileType;
import nts.uk.shr.infra.file.storage.filetype.defines.SamplePackFileType;

final class FileTypeDef {
	
	private static Map<String, FileTypeDescription> map = new HashMap<>();
	static {
		Arrays.asList(
				// Add file type descriptions here
				new SampleFileType(),
				new SamplePackFileType()
				
				).stream().forEach(d -> {
					map.put(d.name(), d);
				});
	}

	public static Optional<FileTypeDescription> of(String nameOfFileType) {
		return Optional.ofNullable(map.get(nameOfFileType));
	}
}
