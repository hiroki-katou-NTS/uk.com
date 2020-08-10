package nts.uk.ctx.at.schedule.app.find.shift.weeklyworkday;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author datnk
 *
 */
@Value
public class WeeklyWorkDayDto {

    private String companyId;

    private List<WorkdayPatternItemDto> workdayPatternItemDtoList;

    public WeeklyWorkDayDto toDto(WeeklyWorkDayPattern weeklyWorkDayPattern) {
        List<WorkdayPatternItemDto> workdayPatternItemDtoList = weeklyWorkDayPattern.getListWorkdayPatternItem().stream().map(item ->
            new WorkdayPatternItemDto(item.getDayOfWeek().value, item.getWorkdayDivision().value)
        ).collect(Collectors.toList());
        return new WeeklyWorkDayDto(weeklyWorkDayPattern.getCompanyId().v(), workdayPatternItemDtoList);
    }

}
