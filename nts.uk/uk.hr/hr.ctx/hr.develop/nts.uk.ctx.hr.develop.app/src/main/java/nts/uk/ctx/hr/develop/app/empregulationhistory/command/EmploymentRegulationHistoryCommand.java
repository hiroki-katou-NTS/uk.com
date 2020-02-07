package nts.uk.ctx.hr.develop.app.empregulationhistory.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.app.empregulationhistory.dto.DateHistoryItemDtoParam;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmploymentRegulationHistoryCommand {

	@Inject
	private EmploymentRegulationHistoryService service;
	
	public String add(DateHistoryItemDtoParam dateHistoryItem) {
		return service.addEmpRegulationHist(AppContexts.user().companyId(), GeneralDate.fromString(dateHistoryItem.startDate, "yyyy/MM/dd"));
	}
	
	public void update(DateHistoryItemDtoParam dateHistoryItem) {
		service.updateEmpRegulationHist(AppContexts.user().companyId(), dateHistoryItem.historyId, GeneralDate.fromString(dateHistoryItem.startDate, "yyyy/MM/dd"));
	}
	
	public void remove(DateHistoryItemDtoParam dateHistoryItem) {
		service.removeEmpRegulationHist(AppContexts.user().companyId(), dateHistoryItem.historyId);
	}
}
