package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の休暇使用時間
 * @author shuichu_ishida
 */
public interface VacationUseTimeOfMonthlyRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param vacationUseTimeOfMonthly 月別実績の休暇使用時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			VacationUseTimeOfMonthly vacationUseTimeOfMonthly);
}
