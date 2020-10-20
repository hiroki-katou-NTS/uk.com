package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

import java.util.List;

import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;

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
	/**
	 * 当月以降の積立年休使用数・残数を取得する
	 * RequestList364 - ver2
	 * @param employeeId
	 * @param period
	 * @return
	 */
	List<RsvLeaUsedCurrentMonExport> getRemainRsvAnnAfCurMonV2(String employeeId, YearMonthPeriod period, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets);
}
