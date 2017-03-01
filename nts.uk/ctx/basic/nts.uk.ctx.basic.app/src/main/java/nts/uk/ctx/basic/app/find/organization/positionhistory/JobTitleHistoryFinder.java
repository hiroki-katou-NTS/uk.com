package nts.uk.ctx.basic.app.find.organization.positionhistory;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistory;
import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JobTitleHistoryFinder {

	@Inject
	private PositionHistoryRepository positionHistoryRepository;
	

	public List<JobTitleHisDto> getAllHistory() {
		String companyCode = AppContexts.user().companyCode();
		return positionHistoryRepository.findAllHistory(companyCode).stream().map(e -> {
			return convertToDto(e);
		}).collect(Collectors.toList());
	}

	private JobTitleHisDto convertToDto(PositionHistory e) {
		JobTitleHisDto positionHisDto = new JobTitleHisDto();
		positionHisDto.setCompanyCode(e.getCompanyCode());
		positionHisDto.setHistoryID(e.getHistoryID());
		positionHisDto.setStartDate(e.getStartDate());
		positionHisDto.setEndDate(e.getEndDate());
		return positionHisDto;
	}





}
