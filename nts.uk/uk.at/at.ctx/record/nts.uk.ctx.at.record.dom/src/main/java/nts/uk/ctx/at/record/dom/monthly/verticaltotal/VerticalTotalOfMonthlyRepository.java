package nts.uk.ctx.at.record.dom.monthly.verticaltotal;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の縦計
 * @author shuichu_ishida
 */
public interface VerticalTotalOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param verticalTotalOfMonthly 月別実績の縦計
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, VerticalTotalOfMonthly verticalTotalOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param verticalTotalOfMonthly 月別実績の縦計
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, VerticalTotalOfMonthly verticalTotalOfMonthly);
}
