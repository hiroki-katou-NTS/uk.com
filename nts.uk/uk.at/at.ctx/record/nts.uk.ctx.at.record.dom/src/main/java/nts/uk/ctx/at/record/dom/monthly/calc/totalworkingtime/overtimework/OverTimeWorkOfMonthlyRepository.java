package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の残業時間
 * @author shuichu_ishida
 */
public interface OverTimeWorkOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param overTimeWorkOfMonthly 月別実績の残業時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeWorkOfMonthly overTimeWorkOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param overTimeWorkOfMonthly 月別実績の残業時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeWorkOfMonthly overTimeWorkOfMonthly);
}
