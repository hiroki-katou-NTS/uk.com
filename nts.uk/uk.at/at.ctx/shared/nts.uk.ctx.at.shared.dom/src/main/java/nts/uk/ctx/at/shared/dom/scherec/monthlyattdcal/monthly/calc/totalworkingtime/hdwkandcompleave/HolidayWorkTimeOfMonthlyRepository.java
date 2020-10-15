package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の休出時間
 * @author shuichu_ishida
 */
public interface HolidayWorkTimeOfMonthlyRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param holidayWorkTimeOfMonthly 月別実績の休出時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly);
}
