package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の通常変形時間
 * @author shuichu_ishida
 */
public interface RegularAndIrregularTimeOfMonthlyRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param regularAndIrregularTimeOfMonthly 月別実績の通常変形時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly);
}
