package nts.uk.ctx.at.function.app.command.alarm;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionAverageMonthCommand;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionPeriodDailyCommand;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionPeriodMonthlyCommand;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionPeriodUnitCommand;
import nts.uk.ctx.at.function.app.command.alarm.extractionrange.ExtractionRangeYearCommand;

@Data
public class CheckConditionCommand {
	private int alarmCategory;
	private List<String> checkConditionCodes;
	private ExtractionPeriodDailyCommand extractionPeriodDaily;
	private ExtractionPeriodUnitCommand extractionPeriodUnit;
	private List<ExtractionPeriodMonthlyCommand> listExtractionMonthly;
	private ExtractionRangeYearCommand extractionYear;
	private ExtractionAverageMonthCommand extractionAverMonth;
}
