package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OneMonthErrorAlarmTimeCommand {
	
	public Integer error;
	
	public Integer alarm;
	
	public OneMonthErrorAlarmTime toDomain() {
		
		return OneMonthErrorAlarmTime.of(
				new AgreementOneMonthTime(error),
				new AgreementOneMonthTime(alarm));
	}
}
