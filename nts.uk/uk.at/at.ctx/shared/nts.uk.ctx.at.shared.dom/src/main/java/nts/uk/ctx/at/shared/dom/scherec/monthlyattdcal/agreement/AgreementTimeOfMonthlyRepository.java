package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;

/**
 * リポジトリ：月別実績の36協定時間
 * @author shuichu_ishida
 */
public interface AgreementTimeOfMonthlyRepository {

	/**
	 * 更新
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param agreementTimeOfMonthly 月別実績の36協定時間
	 */
	void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, AgreementTimeOfMonthly agreementTimeOfMonthly);
}
