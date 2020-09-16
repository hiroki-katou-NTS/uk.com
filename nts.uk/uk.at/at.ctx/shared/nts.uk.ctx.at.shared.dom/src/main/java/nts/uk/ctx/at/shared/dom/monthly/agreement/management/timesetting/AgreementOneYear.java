package nts.uk.ctx.at.shared.dom.monthly.agreement.management.timesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.oneyear.OneYearTime;

/** ３６協定1年間 */
@AllArgsConstructor
@Getter
public class AgreementOneYear {

	/** 基本設定 */
	private OneYearErrorAlarmTime basic;
	/** 特例条項による上限 */
	private OneYearTime specConditionLimit;
	
	public AgreementOneYear() {
		this.basic = new OneYearErrorAlarmTime();
		this.specConditionLimit = new OneYearTime();
	}
	
	/** エラーチェック */
	public AgreementTimeStatusOfMonthly check(AgreementOneYearTime agreementTarget,
			AgreementOneYearTime legalUpperTarget) {
		/** TODO: 要確認　*/
		/** エラーチェック */
		val legalState = this.specConditionLimit.check(legalUpperTarget);
		
		/** 法定上限対象時間の超過状態をチェック */
		if (legalState == ExcessState.ALARM_OVER) {
			
			return AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM;
		}
		if (legalState == ExcessState.ERROR_OVER) {
			
			return AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR;
		}
		if (legalState == ExcessState.UPPER_LIMIT_OVER) {
			
			return AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY;
		}
		
		if (agreementTarget.greaterThanOrEqualTo(this.basic.getError())) {
			return AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR;
		}
		
		if (agreementTarget.greaterThanOrEqualTo(this.basic.getAlarm())) {
			return AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM;
		}
		
		return AgreementTimeStatusOfMonthly.NORMAL;
	}
}
