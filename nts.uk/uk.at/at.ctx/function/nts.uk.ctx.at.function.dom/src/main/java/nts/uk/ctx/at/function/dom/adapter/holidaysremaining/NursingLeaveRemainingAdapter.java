package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface NursingLeaveRemainingAdapter {
	/**
	 * RequestList207: 期間内の介護残を集計する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param period
	 * @return
	 */
	NursingLeaveCurrentSituationImported getNursingLeaveCurrentSituation(String companyId, String employeeId,
			DatePeriod period);
}
