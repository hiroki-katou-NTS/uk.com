package nts.uk.shr.infra.file.constraint;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import nts.arc.layer.infra.file.constraint.FileStereo;
import nts.uk.shr.infra.file.constraint.stereotypes.DocumentFileStereoType;
import nts.uk.shr.infra.file.constraint.stereotypes.FlowMenuStereoType;

public class FileStereoFactory {
	private static Map<String, Class<? extends FileStereo>> fileStereos;
	static {
		fileStereos = new HashMap<>();
		fileStereos.put("flowmenu", FlowMenuStereoType.class);
		fileStereos.put("document", DocumentFileStereoType.class);
	}

	public static Optional<FileStereo> of(String stereoName) {
		if (!Objects.nonNull(stereoName)) return Optional.empty();
		Class<? extends FileStereo> fileStereo = fileStereos.get(stereoName);
		if (fileStereo == null) {
			return Optional.empty();
		}
		try {
			return Optional.of((FileStereo) fileStereo.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			return Optional.empty();
		}
	}
}
