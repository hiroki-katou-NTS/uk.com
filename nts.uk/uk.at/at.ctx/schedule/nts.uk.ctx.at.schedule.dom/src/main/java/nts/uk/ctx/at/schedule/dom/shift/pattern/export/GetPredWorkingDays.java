package nts.uk.ctx.at.schedule.dom.shift.pattern.export;

import java.util.Map;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.schedule.dom.shift.pattern.export.GetPredWorkingDaysImpl.Require;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 所定労働日数を取得する
 * @author shuichi_ishida
 */
public interface GetPredWorkingDays {

	/**
	 * 指定期間の所定労働日数を取得する(大塚用)
	 * @param period 期間
	 * @return 所定労働日数
	 */
	public AttendanceDaysMonth byPeriod(DatePeriod period);

	/**
	 * 指定期間の所定労働日数を取得する(大塚用)
	 * @param period 期間
	 * @param workTypeMap 勤務種類マップ
	 * @return 所定労働日数
	 */
	public AttendanceDaysMonth byPeriod(DatePeriod period, Map<String, WorkType> workTypeMap);
	public AttendanceDaysMonth byPeriodRequire(Require cacheCarrier, DatePeriod period,Map<String, WorkType> workTypeMap);
}
