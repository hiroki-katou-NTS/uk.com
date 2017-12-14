package nts.uk.shr.infra.file.storage.info;

import lombok.Value;

@Value
public class StoredFileSecurityInfo {

	private final String fileId;
	
	private final StoredFileOwner owner;
	
}
