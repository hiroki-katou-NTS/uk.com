package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OneMonthErrorAlarmTimeDto {
	
	public Integer error;
	
	public Integer alarm;
	
	public static OneMonthErrorAlarmTimeDto fromDomain(OneMonthErrorAlarmTime alarmTime) {
		
		return new OneMonthErrorAlarmTimeDto(
				alarmTime.getError().v(),
				alarmTime.getAlarm().v());
	}
	
	 
	
}
