package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績のフレックス時間
 * @author shuichu_ishida
 */
public interface FlexTimeOfMonthlyRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param flexTimeOfMonthly 月別実績のフレックス時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, FlexTimeOfMonthly flexTimeOfMonthly);
}
