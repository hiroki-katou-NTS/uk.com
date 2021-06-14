package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr;

import java.util.List;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 月別実績を集計する
 * @author masaaki_jinno
 *
 */
public interface MonthlyAggregationRemainingNumber {

	/**
	 * 月別実績を集計する
	 * @param require
	 * @param cacheCarrier
	 * @param period
	 * @param interimRemainMngMode
	 * @param isCalcAttendanceRate
	 * @return
	 */
	AggregateMonthlyRecordValue aggregation(CacheCarrier cacheCarrier, DatePeriod period,
			String companyId, String employeeId, YearMonth yearMonth, ClosureId closureId,   ClosureDate closureDate,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			MonthlyCalculatingDailys monthlyCalculatingDailys,
			InterimRemainMngMode interimRemainMngMode, boolean isCalcAttendanceRate);

	List<DailyInterimRemainMngData> createDailyInterimRemainMngs(CacheCarrier cacheCarrier,
														String companyId,
														String employeeId,
														DatePeriod period,
														MonAggrCompanySettings comSetting,
														MonthlyCalculatingDailys dailys);

}
