package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import nts.arc.error.BusinessException;

/** 1年間のエラーアラーム時間 */
@Getter
public class OneYearErrorAlarmTime {

	/** エラー時間 */
	private AgreementOneYearTime error;
	
	/** アラーム時間 */
	private AgreementOneYearTime alarm;
	
	public OneYearErrorAlarmTime() {
		this.alarm = new AgreementOneYearTime(0);
		this.error = new AgreementOneYearTime(0);
	}
	
	private OneYearErrorAlarmTime(AgreementOneYearTime error, AgreementOneYearTime alarm) {
		this.error = error;
		this.alarm = alarm;
	}
	
	public static OneYearErrorAlarmTime of(AgreementOneYearTime error, AgreementOneYearTime alarm) {

		/** 不変条件: @エラー時間 >= @アラーム時間 */
		if (alarm.greaterThan(error)) {
			throw new BusinessException("Msg_59", "KMK008_67", "KMK008_66");
		}

		return new OneYearErrorAlarmTime(error, alarm);
	}
	
	/** エラー時間を超えているか */
	public Pair<Boolean, AgreementOneYearTime> isErrorTimeOver(AgreementOneYearTime applyTime) {
		
		/** ＠エラー時間 < 申請時間 */
		boolean isOver = error.lessThan(applyTime);
		
		return Pair.of(isOver, error);
	}
	
	/** アラーム時間を計算する */
	public AgreementOneYearTime calcAlarmTime(AgreementOneYearTime applyTime) {
		
		/** $アラーム時間 = 申請時間 - (＠エラー時間 - @アラーム時間) */
		AgreementOneYearTime alarmTime = applyTime.minusMinutes(error.valueAsMinutes() - alarm.valueAsMinutes());
		
		return alarmTime.valueAsMinutes() > 0 ? alarmTime : new AgreementOneYearTime(0);
	}
}
