package nts.uk.ctx.at.record.dom.workrecord.log;

import java.util.Optional;

public interface CaseSpecExeContentRepository {

	Optional<CaseSpecExeContent> getCaseSpecExeContentById(String caseSpecExeContentID );
	
}
