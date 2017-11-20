package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の年休使用時間
 * @author shuichu_ishida
 */
public interface AnnualLeaveUseTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param annualLeaveUseTimeOfMonthly 月別実績の年休使用時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AnnualLeaveUseTimeOfMonthly annualLeaveUseTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param annualLeaveUseTimeOfMonthly 月別実績の年休使用時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AnnualLeaveUseTimeOfMonthly annualLeaveUseTimeOfMonthly);
}
