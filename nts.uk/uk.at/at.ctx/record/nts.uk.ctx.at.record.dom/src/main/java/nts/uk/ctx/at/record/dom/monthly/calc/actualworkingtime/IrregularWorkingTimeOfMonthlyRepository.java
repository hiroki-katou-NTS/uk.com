package nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 月別実績の変形労働時間
 * @author shuichu_ishida
 */
public interface IrregularWorkingTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param irregularWorkingTimeOfMonthly 月別実績の変形労働時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			IrregularWorkingTimeOfMonthly irregularWorkingTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param irregularWorkingTimeOfMonthly 月別実績の変形労働時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			IrregularWorkingTimeOfMonthly irregularWorkingTimeOfMonthly);
}
