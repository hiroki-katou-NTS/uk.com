package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の休出時間
 * @author shuichu_ishida
 */
public interface HolidayWorkTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param holidayWorkTimeOfMonthly 月別実績の休出時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param holidayWorkTimeOfMonthly 月別実績の休出時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly);
}
