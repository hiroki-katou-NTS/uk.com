package nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の通常変形時間
 * @author shuichu_ishida
 */
public interface RegularAndIrregularTimeOfMonthlyRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param regularAndIrregularTimeOfMonthly 月別実績の通常変形時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param regularAndIrregularTimeOfMonthly 月別実績の通常変形時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly);
}
