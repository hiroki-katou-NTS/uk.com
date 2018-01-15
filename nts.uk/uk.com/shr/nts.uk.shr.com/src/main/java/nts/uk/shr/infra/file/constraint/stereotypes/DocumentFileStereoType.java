package nts.uk.shr.infra.file.constraint.stereotypes;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.infra.file.constraint.FileStereo;

@Getter
public class DocumentFileStereoType implements FileStereo {

	private long limitedSize = 10485760;
	private List<String> supportedExtension = new ArrayList<>();
	private List<String> downloadRights = new ArrayList<>();
	private List<String> uploadRights = new ArrayList<>();
	private List<String> deleteRights = new ArrayList<>();

}
