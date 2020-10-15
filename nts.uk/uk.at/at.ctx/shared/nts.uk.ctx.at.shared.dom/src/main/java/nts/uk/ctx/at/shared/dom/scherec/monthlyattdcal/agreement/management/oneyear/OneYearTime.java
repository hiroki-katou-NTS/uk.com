package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;

/** １年間時間 */
public class OneYearTime {

	/** エラーアラーム時間 */
	@Setter
	private OneYearErrorAlarmTime erAlTime;
	/** 上限時間 */
	private AgreementOneYearTime upperLimit;
	
	public OneYearTime() {
		this.erAlTime = new OneYearErrorAlarmTime();
		this.upperLimit = new AgreementOneYearTime(0);
	}

	private OneYearTime(OneYearErrorAlarmTime erAlTime, AgreementOneYearTime upperLimit) {
		this.erAlTime = erAlTime;
		this.upperLimit = upperLimit;
	}
	
	public static OneYearTime of(OneYearErrorAlarmTime erAlTime, AgreementOneYearTime upperLimit) {

		/** 不変条件: @上限時間 ≧ @エラーアラーム時間.エラー時間 */
		if (upperLimit.lessThan(erAlTime.getError())) {
			throw new BusinessException("Msg_59", "KMK008_66", "KMK008_129");
		}

		return new OneYearTime(erAlTime, upperLimit);
	}
	
	/** エラーチェック */
	public ExcessState check(AgreementOneYearTime target) {
		
		if (target.v() > upperLimit.valueAsMinutes()) {
			return ExcessState.UPPER_LIMIT_OVER;
		}
		
		if (target.v() > erAlTime.getError().valueAsMinutes()) {
			return ExcessState.ERROR_OVER;
		}
		
		if (target.v() > erAlTime.getAlarm().valueAsMinutes()) {
			return ExcessState.ALARM_OVER;
		}
		
		return ExcessState.NORMAL;
	}
	
	/** エラー時間を超えているか */
	public Pair<Boolean, AgreementOneYearTime> isErrorTimeOver(AgreementOneYearTime applyTime) {
		
		/** ＠エラーアラーム時間.エラー時間を超えているか(申請時間) */
		return erAlTime.isErrorTimeOver(applyTime);
	}
	
	/** アラーム時間を計算する */
	public AgreementOneYearTime calcAlarmTime(AgreementOneYearTime applyTime) {
		
		/** ＠エラーアラーム時間.アラーム時間を計算する(申請時間) */
		return erAlTime.calcAlarmTime(applyTime);
	}
}
