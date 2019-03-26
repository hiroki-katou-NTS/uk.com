package nts.uk.ctx.at.function.app.find.alarm.checkcondition.annualholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualHolidayAlarmConditionDto {

	private AlarmCheckConAgrDto alCheckConAgrDto;
	
	private AlarmCheckSubConAgrDto alCheckSubConAgrDto;
}
