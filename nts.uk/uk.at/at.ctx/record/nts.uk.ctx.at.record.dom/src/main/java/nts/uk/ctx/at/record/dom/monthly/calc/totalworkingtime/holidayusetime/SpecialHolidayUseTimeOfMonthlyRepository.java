package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の特別休暇使用時間
 * @author shuichu_ishida
 */
public interface SpecialHolidayUseTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param specialHolidayUseTimeOfMonthly 月別実績の特別休暇使用時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			SpecialHolidayUseTimeOfMonthly specialHolidayUseTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param specialHolidayUseTimeOfMonthly 月別実績の特別休暇使用時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			SpecialHolidayUseTimeOfMonthly specialHolidayUseTimeOfMonthly);
}
