package nts.uk.ctx.at.request.dom.settting.worktype.history;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
@Stateless
public class VactionHistoryRulesServiceImpl implements IVactionHistoryRulesService{

	@Inject
	private VacationHistoryRepository vacationHistoryRepository;
	@Override
	public List<PlanVacationHistory> getPlanVacationHistoryByDate(String companyId, GeneralDate inputDate) {
		List<PlanVacationHistory> planVacationHistoryList = new ArrayList<>();
		// TODO 
		return planVacationHistoryList;
	}

}
