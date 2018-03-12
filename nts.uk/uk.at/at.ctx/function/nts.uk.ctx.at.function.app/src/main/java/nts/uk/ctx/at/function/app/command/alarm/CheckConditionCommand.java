package nts.uk.ctx.at.function.app.command.alarm;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionPeriodDailyCommand;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionPeriodUnitCommand;

@Data
public class CheckConditionCommand {
	private int alarmCategory;
	private List<String> checkConditionCodes;	
	private ExtractionPeriodDailyCommand extractionPeriodDaily;
	private ExtractionPeriodUnitCommand extractionPeriodUnit;
}
