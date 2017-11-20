package nts.uk.ctx.at.record.dom.monthly.calc;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の月の計算
 * @author shuichu_ishida
 */
public interface MonthlyCalculationRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param monthlyCalculation 月別実績の月の計算
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, MonthlyCalculation monthlyCalculation);
	
	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param monthlyCalculation 月別実績の月の計算
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, MonthlyCalculation monthlyCalculation);
}
