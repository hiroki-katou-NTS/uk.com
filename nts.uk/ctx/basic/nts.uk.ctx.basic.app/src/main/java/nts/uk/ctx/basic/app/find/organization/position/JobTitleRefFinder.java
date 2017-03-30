package nts.uk.ctx.basic.app.find.organization.position;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.position.JobTitleRef;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JobTitleRefFinder {
	@Inject
	private PositionRepository repository;
	
	
	public List<JobTitleRefDto> findAllJobTitleRef(String jobCode,String historyId) {
		String companyCode = AppContexts.user().companyCode();

		return repository.findAllJobTitleRef(companyCode,historyId,jobCode)
				.stream().map(e->{return convertToDto(e);}).collect(Collectors.toList());
	}
	
	private JobTitleRefDto convertToDto(JobTitleRef e) {
		
		JobTitleRefDto positionDto = new JobTitleRefDto();
		positionDto.setAuthorizationCode(e.getAuthorizationCode().v());
		positionDto.setHistoryId(e.getHistoryId());
		positionDto.setJobCode(e.getJobCode().v());
		positionDto.setReferenceSettings(e.getReferenceSettings().value);
		return positionDto;
	}
}
