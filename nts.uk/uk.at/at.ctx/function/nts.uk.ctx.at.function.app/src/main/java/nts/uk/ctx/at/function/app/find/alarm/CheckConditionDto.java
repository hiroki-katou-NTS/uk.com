package nts.uk.ctx.at.function.app.find.alarm;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.find.alarm.extractionrange.ExtractionPeriodDailyDto;

@Data
@AllArgsConstructor
public class CheckConditionDto {
	private int alarmCategory;
	private List<String> checkConditionCodes;	
	private ExtractionPeriodDailyDto extractionDaily;
}
