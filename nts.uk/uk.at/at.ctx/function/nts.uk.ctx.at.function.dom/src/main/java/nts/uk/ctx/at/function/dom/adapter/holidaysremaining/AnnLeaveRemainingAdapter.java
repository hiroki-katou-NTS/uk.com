package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;


public interface AnnLeaveRemainingAdapter {
	public List<AnnLeaveUsageStatusOfThisMonthImported> getAnnLeaveUsageOfThisMonth(String employeeId,
			DatePeriod datePeriod);
	
	public AnnLeaveOfThisMonthImported getAnnLeaveOfThisMonth(String employeeId);
}
