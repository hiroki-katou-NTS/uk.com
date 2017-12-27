package nts.uk.ctx.at.function.app.command.alarm;

import java.util.List;
import lombok.Data;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionRangeCommand;

@Data
public class CheckConditionDto {
	private int alarmCategory;
	private List<String> checkConditionCodes;	
	private ExtractionRangeCommand extractPeriod;
	
}
