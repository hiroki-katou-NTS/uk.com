package nts.uk.ctx.at.shared.dom.scherec.attdstatus;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 出勤状態を取得する
 * @author shuichu_ishida
 */
public interface GetAttendanceStatus {

	/**
	 * データ設定
	 * @param employeeId 社員ID
	 * @param period 期間
	 */
	GetAttendanceStatus setData(String employeeId, DatePeriod period);

	/**
	 * 出勤状態を判断する
	 * @param ymd 年月日
	 * @return true：出勤している、false：出勤していない
	 */
	boolean isAttendanceDay(GeneralDate ymd);
	
	/**
	 * 2回目の打刻が存在するか確認
	 * @param ymd 年月日
	 * @return true：存在する、false：存在しない
	 */
	boolean isTwoTimesStampExists(GeneralDate ymd);

	/**
	 * 総労働時間を取得する
	 * @param ymd 年月日
	 * @return 総労働時間
	 */
	AttendanceTime getTotalTime(GeneralDate ymd);
}
