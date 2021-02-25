package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：時間外超過
 * @author shuichu_ishida
 */
public interface ExcessOutsideWorkRepository {

	/**
	 * 追加
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param excessOutsideWork 時間外超過
	 */
	void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			ExcessOutsideWork excessOutsideWork);

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param excessOutsideWork 時間外超過
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			ExcessOutsideWork excessOutsideWork);
	
	/**
	 * 削除　（親キー）
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 */
	void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey);
}
