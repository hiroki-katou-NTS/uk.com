package nts.uk.ctx.basic.app.find.organization.position;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.position.JobTitle;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JobTitleFinder {

	@Inject
	private PositionRepository repository;
		
	public List<JobTitleDto> findAllPosition(String historyId) {
		String companyCode = AppContexts.user().companyCode();
		return repository.findAllPosition(companyCode,historyId)
				.stream().map(e->{return convertToDto(e);}).collect(Collectors.toList());
	}

	private JobTitleDto convertToDto(JobTitle position) {
		JobTitleDto positionDto = new JobTitleDto();
		positionDto.setJobCode(position.getJobCode().v());
		positionDto.setJobName(position.getJobName().v());
		positionDto.setHistoryId(position.getHistoryId());
		positionDto.setMemo(position.getMemo().v());
		positionDto.setJobOutCode(position.getJobOutCode().v());
		positionDto.setHiterarchyOrderCode(position.getHiterarchyOrderCode().v());
		positionDto.setPresenceCheckScopeSet(position.getPresenceCheckScopeSet().value);
		return positionDto;
	}

	
}
