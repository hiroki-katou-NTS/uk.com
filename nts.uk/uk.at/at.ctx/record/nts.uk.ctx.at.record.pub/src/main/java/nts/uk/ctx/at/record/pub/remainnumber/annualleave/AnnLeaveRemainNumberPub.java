package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AnnLeaveRemainNumberPub {
	
	/**
	 * RequestList #No.265
	 * @param employeeId
	 * @return
	 */
	AnnLeaveOfThisMonth getAnnLeaveOfThisMonth(String employeeId);

	/**
	 * RequestList #No.363
	 * @param employeeId
	 * @param datePeriod
	 * @return
	 */
	List<AggrResultOfAnnualLeaveEachMonth> getAnnLeaveRemainAfterThisMonth(String employeeId, DatePeriod datePeriod);
}
