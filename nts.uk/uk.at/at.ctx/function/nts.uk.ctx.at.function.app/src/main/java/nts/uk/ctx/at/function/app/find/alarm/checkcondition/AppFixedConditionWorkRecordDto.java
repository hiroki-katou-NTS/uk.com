package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppFixedConditionWorkRecordDto {

	private String appAlarmConId;
	
	private int no;
	
	private String name;

	private String displayMessage;

	private boolean useAtr;

	private int erAlAtr;
}
