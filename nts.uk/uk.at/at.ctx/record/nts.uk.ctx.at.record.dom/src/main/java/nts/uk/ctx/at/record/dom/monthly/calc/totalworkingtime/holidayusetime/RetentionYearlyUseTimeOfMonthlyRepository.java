package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の積立年休使用時間
 * @author shuichu_ishida
 */
public interface RetentionYearlyUseTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param retentionYearlyUseTimeOfMonthly 月別実績の積立年休使用時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RetentionYearlyUseTimeOfMonthly retentionYearlyUseTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param retentionYearlyUseTimeOfMonthly 月別実績の積立年休使用時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RetentionYearlyUseTimeOfMonthly retentionYearlyUseTimeOfMonthly);
}
