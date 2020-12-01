package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr;

import java.util.Map;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc.RequireM7;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc.RequireM8;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;

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
	AggregateMonthlyRecordValue aggregation(
			RequireM8 require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode, boolean isCalcAttendanceRate);

	Map<GeneralDate, DailyInterimRemainMngData> createDailyInterimRemainMngs(
			RequireM7 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			DatePeriod period,
			MonAggrCompanySettings comSetting,
			MonthlyCalculatingDailys dailys);
}
