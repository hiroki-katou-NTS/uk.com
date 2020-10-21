package nts.uk.ctx.at.shared.dom.scherec.workinfo;

import java.util.Map;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;

/**
 * 勤務情報を取得する
 * @author shuichu_ishida
 */
public interface GetWorkInfo {

	/**
	 * 実績の勤務情報を取得する
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @return 勤務情報
	 */
	Optional<WorkInformation> getRecord(CacheCarrier cacheCarrier, String employeeId, GeneralDate ymd);

	/**
	 * 実績の勤務情報を取得する
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 勤務情報マップ
	 */
	Map<GeneralDate, WorkInformation> getRecordMap(CacheCarrier cacheCarrier, String employeeId, DatePeriod period);
	
}
