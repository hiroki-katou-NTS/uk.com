package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * 集計残業時間
 * @author shuichu_ishida
 */
public interface AggregateOverTimeWorkRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateOverTimeWork 集計残業時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTimeWork aggregateOverTimeWork);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateOverTimeWork 集計残業時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTimeWork aggregateOverTimeWork);
	
	/**
	 * 削除　（親キー）
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 */
	void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey);
}
