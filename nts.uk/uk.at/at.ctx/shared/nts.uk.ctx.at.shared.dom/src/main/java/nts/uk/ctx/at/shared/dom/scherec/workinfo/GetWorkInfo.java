package nts.uk.ctx.at.shared.dom.scherec.workinfo;

import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	Optional<WorkInformation> getRecord(String employeeId, GeneralDate ymd);
	
	/**
	 * 予定の勤務情報を取得する
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @return 勤務情報
	 */
	Optional<WorkInformation> getSchedule(String employeeId, GeneralDate ymd);

	/**
	 * 実績の勤務情報を取得する
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 勤務情報マップ
	 */
	Map<GeneralDate, WorkInformation> getRecordMap(String employeeId, DatePeriod period);
	
	/**
	 * 予定の勤務情報を取得する
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 勤務情報マップ
	 */
	Map<GeneralDate, WorkInformation> getScheduleMap(String employeeId, DatePeriod period);
}
