package nts.uk.ctx.at.function.app.find.alarmworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.find.alarm.extractionrange.*;

import java.util.List;

@Data
@AllArgsConstructor
public class WkpCheckConditionDto {
	private int alarmCategory;
	private List<String> checkConditionCodes;	
	private ExtractionPeriodDailyDto extractionDaily;
	private ExtractionPeriodUnitDto extractionUnit;
	private List<ExtractionPeriodMonthlyDto> listExtractionMonthly;
	private ExtractionRangeYearDto extractionYear;
	private ExtractionAverMonthDto extractionAverMonth;
}
