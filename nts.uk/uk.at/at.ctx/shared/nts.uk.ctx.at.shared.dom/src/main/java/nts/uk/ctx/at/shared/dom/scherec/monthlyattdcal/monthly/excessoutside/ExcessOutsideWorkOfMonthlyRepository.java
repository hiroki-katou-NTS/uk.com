package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の時間外超過
 * @author shuichu_ishida
 */
public interface ExcessOutsideWorkOfMonthlyRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param excessOutsideWorkOfMonthly 月別実績の時間外超過
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			ExcessOutsideWorkOfMonthly excessOutsideWorkOfMonthly);
}
