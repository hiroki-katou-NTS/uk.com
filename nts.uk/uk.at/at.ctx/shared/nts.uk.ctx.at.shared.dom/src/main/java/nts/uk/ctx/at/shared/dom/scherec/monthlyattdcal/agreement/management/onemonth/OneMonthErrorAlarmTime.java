package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import nts.arc.error.BusinessException;

/** 1ヶ月のエラーアラーム時間 */
@Getter
public class OneMonthErrorAlarmTime {

	/** エラー時間 */
	private AgreementOneMonthTime error;
	/** アラーム時間 */ 
	private AgreementOneMonthTime alarm;
	
	public OneMonthErrorAlarmTime() {
		this.alarm = new AgreementOneMonthTime(0);
		this.error = new AgreementOneMonthTime(0);
	}
	
	private OneMonthErrorAlarmTime(AgreementOneMonthTime error, AgreementOneMonthTime alarm) {
		this.alarm = alarm;
		this.error = error;
	}
	
	public static OneMonthErrorAlarmTime of(AgreementOneMonthTime error, AgreementOneMonthTime alarm) {

		/** 不変条件: @エラー時間 >= @アラーム時間 */
		if (alarm.greaterThan(error)) {
			throw new BusinessException("Msg_59", "KMK008_67", "KMK008_66");
		}

		return new OneMonthErrorAlarmTime(error, alarm);
	}
	
	/** エラー時間を超えているか */
	public Pair<Boolean, AgreementOneMonthTime> isErrorTimeOver(AgreementOneMonthTime applyTime) {
		
		/** ＠エラー時間 < 申請時間 */
		boolean isOver = error.lessThan(applyTime);
		
		return Pair.of(isOver, error);
	}
	
	/** アラーム時間を計算する */
	public AgreementOneMonthTime calcAlarmTime(AgreementOneMonthTime applyTime) {
		
		/** $アラーム時間 = 申請時間 - (＠エラー時間 - @アラーム時間) */
		AgreementOneMonthTime alarmTime = applyTime.minusMinutes(error.valueAsMinutes() - alarm.valueAsMinutes());
		
		return alarmTime.valueAsMinutes() > 0 ? alarmTime : new AgreementOneMonthTime(0);
	}
}
