package nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 社員の月毎の確定済み積立年休を取得する
 * @author shuichu_ishida
 */
public interface GetConfirmedReserveLeave {

	/**
	 * 社員の月毎の確定済み積立年休を取得する
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 年休利用状況リスト
	 */
	// RequestList258
	List<ReserveLeaveUsageExport> algorithm(String employeeId, YearMonthPeriod period);
}
