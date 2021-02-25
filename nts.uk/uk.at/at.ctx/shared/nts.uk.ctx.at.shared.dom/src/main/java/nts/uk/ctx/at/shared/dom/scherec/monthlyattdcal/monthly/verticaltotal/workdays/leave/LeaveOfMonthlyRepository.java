package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.leave;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の休業
 * @author shuichu_ishida
 */
public interface LeaveOfMonthlyRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param leaveOfMonthly 月別実績の休業
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, LeaveOfMonthly leaveOfMonthly);
}
