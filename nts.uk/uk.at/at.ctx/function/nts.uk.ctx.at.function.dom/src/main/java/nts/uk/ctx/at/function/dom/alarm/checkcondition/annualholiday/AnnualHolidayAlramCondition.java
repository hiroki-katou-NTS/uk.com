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
public class AnnualHolidayAlramCondition extends ExtractionCondition{

	private AlarmCheckConAgr alarmCheckConAgr;
	
	private AlarmCheckSubConAgr alarmCheckSubConAgr;
	
	@Override
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof AnnualHolidayAlramCondition) {
			AnnualHolidayAlramCondition value = (AnnualHolidayAlramCondition) extractionCondition;
			this.alarmCheckConAgr = value.alarmCheckConAgr;
			this.alarmCheckSubConAgr = value.alarmCheckSubConAgr;
		}
		
	}

}
