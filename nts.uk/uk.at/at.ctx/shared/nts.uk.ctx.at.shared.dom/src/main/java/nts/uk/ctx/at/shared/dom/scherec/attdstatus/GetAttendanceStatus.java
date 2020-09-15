package nts.uk.ctx.at.shared.dom.scherec.attdstatus;

import java.util.Map;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 出勤状態を取得する
 * @author shuichu_ishida
 */
public interface GetAttendanceStatus {

	/**
	 * 取得
	 * @param employeeId 社員ID
	 * @param targetDate 対象日
	 * @return 出勤状態
	 */
	Optional<AttendanceStatus> get(CacheCarrier cacheCarrier, String employeeId, GeneralDate targetDate);

	/**
	 * 取得
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 出勤状態マップ（年月日別）
	 */
	Map<GeneralDate, AttendanceStatus> getMap(CacheCarrier cacheCarrier, String employeeId, DatePeriod period);
}
