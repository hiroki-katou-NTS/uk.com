package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：集計休出時間
 * @author shuichu_ishida
 */
public interface AggregateHolidayWorkTimeRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateHolidayWorkTime 集計休出時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateHolidayWorkTime 集計休出時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime);
	
	/**
	 * 削除　（親キー）
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 */
	void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey);
}
