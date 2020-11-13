package nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist;

import lombok.Data;

import java.util.List;

@Data
public class ExtractAlarmListWorkPlaceCommand {
    private List<String> workplaceIds;
    private String alarmPatternCode;
    private List<CategoryPeriodCommand> categoryPeriods;
}
