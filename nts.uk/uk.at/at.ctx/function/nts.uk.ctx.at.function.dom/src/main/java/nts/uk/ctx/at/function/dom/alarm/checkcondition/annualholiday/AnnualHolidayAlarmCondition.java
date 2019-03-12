package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * @author thanhnx
 * 年休アラーム条件
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnualHolidayAlarmCondition extends ExtractionCondition{

	/**
	 * 年休アラームチェック条件
	 */
	private AlarmCheckConAgr alarmCheckConAgr;
	
	/**
	 * 年休アラームチェック対象者条件
	 */
	private AlarmCheckSubConAgr alarmCheckSubConAgr;
	
	@Override
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof AnnualHolidayAlarmCondition) {
			AnnualHolidayAlarmCondition value = (AnnualHolidayAlarmCondition) extractionCondition;
			this.alarmCheckConAgr = value.alarmCheckConAgr;
			this.alarmCheckSubConAgr = value.alarmCheckSubConAgr;
		}
		
	}

}
