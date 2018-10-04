package nts.uk.ctx.at.function.app.find.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeNameAlarmDto {
	private String alarmCode;
	private String alarmName;
	private String alarmNameCode;
}
