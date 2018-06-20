package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 当月以降の積立年休使用数・残数を取得する
 * @author shuichu_ishida
 */
public interface GetRsvLeaNumAfterCurrentMon {

	/**
	 * 当月以降の積立年休使用数・残数を取得する
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 積立年休利用当月状況リスト
	 */
	// RequestList364
	List<RsvLeaUsedCurrentMonExport> algorithm(String employeeId, YearMonthPeriod period);
}
