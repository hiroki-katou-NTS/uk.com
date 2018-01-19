package nts.uk.ctx.at.function.app.command.alarm;

import java.util.List;
import lombok.Data;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionPeriodDailyCommand;

@Data
public class CheckConditionCommand {
	private int alarmCategory;
	private List<String> checkConditionCodes;	
	private ExtractionPeriodDailyCommand extractionPeriodDaily;
	
}
