package nts.uk.shr.com.security.audittrail.start;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProgramScreenId {

	private final String queryParam;
	
	private final String programId;
	
	private final String screenId; 
}
