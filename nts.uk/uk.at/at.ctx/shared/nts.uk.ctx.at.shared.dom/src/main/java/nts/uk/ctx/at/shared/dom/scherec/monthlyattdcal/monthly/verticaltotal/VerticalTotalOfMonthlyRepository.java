package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の縦計
 * @author shuichu_ishida
 */
public interface VerticalTotalOfMonthlyRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param verticalTotalOfMonthly 月別実績の縦計
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, VerticalTotalOfMonthly verticalTotalOfMonthly);
}
