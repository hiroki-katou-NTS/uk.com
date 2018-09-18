package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.StatusOfHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonFunImport;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;

public interface CheckResultRemainMonthlyAdapter {
	
	/**
	 * 
	 * @param checkRemainNumberMonFunImport
	 * @param annualLeaveUsageImport
	 * @return
	 */
	boolean checkAnnualLeaveUsage(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,AnnualLeaveUsageImport annualLeaveUsageImport);
	/**
	 * 
	 * @param checkRemainNumberMonFunImport
	 * @param dayoffCurrentMonthOfEmployeeImport
	 * @return
	 */
	boolean checkDayoffCurrentMonth(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,DayoffCurrentMonthOfEmployeeImport dayoffCurrentMonthOfEmployeeImport);
	/**
	 * 
	 * @param checkRemainNumberMonFunImport
	 * @param statusOfHolidayImported
	 * @return
	 */
	boolean checkStatusOfHoliday(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,StatusOfHolidayImported statusOfHolidayImported);
	/**
	 * 
	 * @param checkRemainNumberMonFunImport
	 * @param reserveLeaveUsageImport
	 * @return
	 */
	boolean checkReserveLeaveUsage(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,ReserveLeaveUsageImport reserveLeaveUsageImport);
	/**
	 * 
	 * @param checkRemainNumberMonFunImport
	 * @param specialHolidayImported
	 * @return
	 */
	boolean checkSpecialHoliday(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,SpecialHolidayImported specialHolidayImported);
}
