package nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave;

import java.util.List;
import java.util.Map;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMerge;

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
	/**
	 * @author hoatt
	 * Doi ung response KDR001
	 * RequestList258 社員の月毎の確定済み積立年休を取得する - ver2
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 年休利用状況リスト
	 */
	List<ReserveLeaveUsageExport> getYearRsvMonthlyVer2(String employeeId, YearMonthPeriod period, Map<YearMonth, List<RemainMerge>> mapRemainMer);
}
