package nts.uk.ctx.at.function.dom.adapter.reserveleave;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

public interface GetReserveLeaveNumbersAdpter {
	
	/**
	 * 社員の積立年休の月初残・使用・残数・未消化を取得する
	 * @param employeeId 社員ID
	 * @return 積立年休現在状況
	 * RequestList268
	 * 
	 */
	ReserveHolidayImported algorithm(String employeeId);
	
	/**
	 * 社員の月毎の確定済み積立年休を取得する
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 年休利用状況リスト
	 * RequestList258
	 */
	List<ReservedYearHolidayImported> algorithm(String employeeId, YearMonthPeriod period);
	
	/**
	 * 当月以降の積立年休使用数・残数を取得する
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 積立年休利用当月状況リスト
	 * RequestList364
	 */
	List<RsvLeaUsedCurrentMonImported> algorithm364(String employeeId, YearMonthPeriod period);

}
