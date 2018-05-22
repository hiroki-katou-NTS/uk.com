package nts.uk.ctx.at.request.dom.settting.worktype.history;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface IVactionHistoryRulesService {
	/**
	 * @param companyId
	 * @param inputDate
	 * @return
	 */
	List<PlanVacationHistory> getPlanVacationHistoryByDate(String companyId, GeneralDate inputDate);
}
