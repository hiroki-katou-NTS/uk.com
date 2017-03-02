package nts.uk.ctx.basic.app.find.organization.positionhistory;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistory;
import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JobHistFinder {

	@Inject
	private PositionHistoryRepository positionHistoryRepository;
	

	public List<JobHisDto> getAllHistory() {
		String companyCode = AppContexts.user().companyCode();
		return positionHistoryRepository.findAllHistory(companyCode).stream().map(e -> {
			return convertToDto(e);
		}).collect(Collectors.toList());
	}

	private JobHisDto convertToDto(PositionHistory e) {
		JobHisDto positionHisDto = new JobHisDto();
		positionHisDto.setCompanyCode(e.getCompanyCode());
		positionHisDto.setHistoryID(e.getHistoryID());
		positionHisDto.setStartDate(e.getStartDate());
		positionHisDto.setEndDate(e.getEndDate());
		return positionHisDto;
	}





}
