package nts.uk.ctx.at.function.app.find.alarm.checkcondition.annualholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCheckConAgrDto {

	private boolean distByPeriod;

	private String displayMessage;

	private int usageObliDay;

}
