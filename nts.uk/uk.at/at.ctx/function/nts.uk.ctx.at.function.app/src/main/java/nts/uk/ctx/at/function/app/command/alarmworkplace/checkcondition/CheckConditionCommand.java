package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Getter;

import java.util.List;

@Getter
public class CheckConditionCommand {

    private int alarmCategory;

    private List<String> checkConditionCodes;

    private ExtractionPeriodDailyCommand extractionDaily;

    private ExtractionPeriodMonthlyCommand listExtractionMonthly;

    private SingleMonthCommand singleMonth;

}
