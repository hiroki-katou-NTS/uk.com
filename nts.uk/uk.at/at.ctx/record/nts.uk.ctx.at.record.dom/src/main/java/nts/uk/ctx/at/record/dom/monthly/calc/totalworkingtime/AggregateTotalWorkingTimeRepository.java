package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：集計総労働時間
 * @author shuichu_ishida
 */
public interface AggregateTotalWorkingTimeRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalWorkingTime aggregateTotalWorkingTime);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalWorkingTime aggregateTotalWorkingTime);
}
