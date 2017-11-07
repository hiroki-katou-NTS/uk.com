package nts.uk.ctx.at.record.app.find.log;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.log.dto.CaseSpecExeContentDto;
import nts.uk.ctx.at.record.dom.workrecord.log.CaseSpecExeContentRepository;

@Stateless
public class CaseSpecExeContentFinder {

	@Inject
	private CaseSpecExeContentRepository caseSpecExeContentRepo;
	
	public CaseSpecExeContentDto getCaseSpecExeContentById(String caseSpecExeContentID ) {
		
		Optional<CaseSpecExeContentDto> data = caseSpecExeContentRepo
				.getCaseSpecExeContentById(caseSpecExeContentID).map(c->CaseSpecExeContentDto.fromDomain(c));
		if(data.isPresent()) {
			return data.get();
		}
		return null;
		
	}
}
