package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の就業時間
 * @author shuichu_ishida
 */
public interface WorkTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param workTimeOfMonthly 月別実績の就業時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			WorkTimeOfMonthly workTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param workTimeOfMonthly 月別実績の就業時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			WorkTimeOfMonthly workTimeOfMonthly);
}
