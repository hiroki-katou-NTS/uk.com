package nts.uk.ctx.basic.app.find.organization.position;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JobHistFinder {

	@Inject
	private PositionRepository repository;
		
	public List<JobHistDto> init() {
		String companyCode = AppContexts.user().companyCode();
		return repository.getAllHistory(companyCode).stream().map(e -> {
			return convertToDto(e);
		}).collect(Collectors.toList());
	}

	private JobHistDto convertToDto(JobHistory e) {
		JobHistDto positionHisDto = new JobHistDto();
		positionHisDto.setCompanyCode(e.getCompanyCode());
		positionHisDto.setHistoryId(e.getHistoryId());
		positionHisDto.setStartDate(e.getStartDate());
		positionHisDto.setEndDate(e.getEndDate());
		return positionHisDto;
	}
	
}
