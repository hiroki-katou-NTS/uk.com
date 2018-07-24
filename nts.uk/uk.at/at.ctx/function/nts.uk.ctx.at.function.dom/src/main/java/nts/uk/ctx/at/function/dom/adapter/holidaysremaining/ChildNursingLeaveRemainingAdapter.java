package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ChildNursingLeaveRemainingAdapter {
	/**
	 * RequestList206: 期間内の子看護残を集計する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param period
	 * @return
	 */
	ChildNursingLeaveCurrentSituationImported getChildNursingLeaveCurrentSituation(String companyId, String employeeId,
			DatePeriod period);
}
