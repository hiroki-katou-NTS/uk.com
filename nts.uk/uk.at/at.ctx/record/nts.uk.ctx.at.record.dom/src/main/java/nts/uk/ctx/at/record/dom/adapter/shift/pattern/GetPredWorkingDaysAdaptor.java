package nts.uk.ctx.at.record.dom.adapter.shift.pattern;

import java.util.Map;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 所定労働日数を取得する
 * @author shuichi_ishida
 */
public interface GetPredWorkingDaysAdaptor {

	/**
	 * 指定期間の所定労働日数を取得する(大塚用)
	 * @param period 期間
	 * @return 所定労働日数
	 */
	// RequestList608
	public AttendanceDaysMonth byPeriod(DatePeriod period);

	/**
	 * 指定期間の所定労働日数を取得する(大塚用)
	 * @param period 期間
	 * @param workTypeMap 勤務種類マップ
	 * @return 所定労働日数
	 */
	public AttendanceDaysMonth byPeriod(CacheCarrier cacheCarrier, DatePeriod period, Map<String, WorkType> workTypeMap);
}