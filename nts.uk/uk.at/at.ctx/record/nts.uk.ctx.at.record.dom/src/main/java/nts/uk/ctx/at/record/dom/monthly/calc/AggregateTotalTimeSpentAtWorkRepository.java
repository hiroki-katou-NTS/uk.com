package nts.uk.ctx.at.record.dom.monthly.calc;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：集計総拘束時間
 * @author shuichu_ishida
 */
public interface AggregateTotalTimeSpentAtWorkRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateTotalTimeSpentAtWork 集計総拘束時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork);
}
