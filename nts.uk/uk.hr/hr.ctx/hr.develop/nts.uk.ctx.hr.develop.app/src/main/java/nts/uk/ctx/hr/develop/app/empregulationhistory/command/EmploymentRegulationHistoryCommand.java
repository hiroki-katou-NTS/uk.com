package nts.uk.ctx.hr.develop.app.empregulationhistory.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.empregulationhistory.dto.DateHistoryItemDto;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmploymentRegulationHistoryCommand {

	@Inject
	private EmploymentRegulationHistoryService service;
	
	public String add(DateHistoryItemDto dateHistoryItem) {
		return service.addEmpRegulationHist(AppContexts.user().companyId(), dateHistoryItem.startDate);
	}
	
	public void update(DateHistoryItemDto dateHistoryItem) {
		service.updateEmpRegulationHist(AppContexts.user().companyId(), dateHistoryItem.historyId, dateHistoryItem.startDate);
	}
	
	public void remove(DateHistoryItemDto dateHistoryItem) {
		service.removeEmpRegulationHist(AppContexts.user().companyId(), dateHistoryItem.historyId);
	}
}
