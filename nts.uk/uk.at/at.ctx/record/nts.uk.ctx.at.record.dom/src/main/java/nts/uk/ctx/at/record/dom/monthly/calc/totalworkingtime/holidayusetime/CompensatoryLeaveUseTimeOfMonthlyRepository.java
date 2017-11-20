package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の代休使用時間
 * @author shuichu_ishida
 */
public interface CompensatoryLeaveUseTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param compensatoryLeaveUseTimeOfMonthly 月別実績の代休使用時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			CompensatoryLeaveUseTimeOfMonthly compensatoryLeaveUseTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param compensatoryLeaveUseTimeOfMonthly 月別実績の代休使用時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			CompensatoryLeaveUseTimeOfMonthly compensatoryLeaveUseTimeOfMonthly);
}
