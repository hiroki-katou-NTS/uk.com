package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の残業時間
 * @author shuichu_ishida
 */
public interface OverTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param overTimeOfMonthly 月別実績の残業時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeOfMonthly overTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param overTimeOfMonthly 月別実績の残業時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeOfMonthly overTimeOfMonthly);
}
