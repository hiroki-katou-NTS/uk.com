package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.premiumtime;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：集計割増時間
 * @author shuichu_ishida
 */
public interface AggregatePremiumTimeRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregatePremiumTime 集計割増時間
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregatePremiumTime aggregatePremiumTime);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregatePremiumTime 集計割増時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregatePremiumTime aggregatePremiumTime);
	
	/**
	 * 削除　（親キー）
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 */
	void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey);
}
