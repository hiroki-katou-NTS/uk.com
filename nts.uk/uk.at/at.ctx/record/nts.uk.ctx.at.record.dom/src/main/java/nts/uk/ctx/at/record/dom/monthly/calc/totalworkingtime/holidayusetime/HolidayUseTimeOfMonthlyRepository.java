package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の休暇使用時間
 * @author shuichu_ishida
 */
public interface HolidayUseTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param holidayUseTimeOfMonthly 月別実績の休暇使用時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayUseTimeOfMonthly holidayUseTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param holidayUseTimeOfMonthly 月別実績の休暇使用時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayUseTimeOfMonthly holidayUseTimeOfMonthly);
}
