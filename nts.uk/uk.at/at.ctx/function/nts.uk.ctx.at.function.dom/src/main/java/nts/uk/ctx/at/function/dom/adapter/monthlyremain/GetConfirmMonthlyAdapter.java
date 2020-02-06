package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

public interface GetConfirmMonthlyAdapter {
	/**
	 * 社員の月毎の確定済み年休を取得する
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 年休利用状況リスト
	 */
	// RequestList255
	List<AnnualLeaveUsageImport> getListAnnualLeaveUsageImport(String employeeId, YearMonthPeriod period);
	
	/**
	 * 社員の月毎の確定済み積立年休を取得する
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 年休利用状況リスト
	 */
	// RequestList258
	List<ReserveLeaveUsageImport> getListReserveLeaveUsageImport(String employeeId, YearMonthPeriod period);
	
	/**
	 * RequesList259 社員の月毎の確定済み代休を取得する
	 * @param employeeId 社員ID
	 * @param startMonth 年月期間
	 * @param endMonth
	 * @return
	 */
	public List<DayoffCurrentMonthOfEmployeeImport> lstDayoffCurrentMonthOfEmployee(String employeeId, YearMonth startMonth, YearMonth endMonth);

	
	
}
