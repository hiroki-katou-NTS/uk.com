package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.goout;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：集計外出
 * @author shuichu_ishida
 */
public interface AggregateGoOutRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateGoOut 集計外出
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateGoOut aggregateGoOut);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateGoOut 集計外出
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateGoOut aggregateGoOut);
	
	/**
	 * 削除　（親キー）
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 */
	void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey);
}
