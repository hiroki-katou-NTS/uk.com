package nts.uk.ctx.at.schedule.app.find.shift.weeklyworkday;

import lombok.*;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author datnk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyWorkDayDto {

    private String companyId;

    private List<WorkdayPatternItemDto> workdayPatternItemDtoList;

    public WeeklyWorkDayDto toDto(WeeklyWorkDayPattern weeklyWorkDayPattern) {
        System.out.println("weeklyWorkDayPattern:   "+weeklyWorkDayPattern.getListWorkdayPatternItem().size());
        List<WorkdayPatternItemDto> workdayPatternItemDtoList = weeklyWorkDayPattern.getListWorkdayPatternItem()
                .stream()
                .map(item ->
            new WorkdayPatternItemDto(item.getDayOfWeek().value, item.getWorkdayDivision().value)
        ).collect(Collectors.toList());
        return new WeeklyWorkDayDto(weeklyWorkDayPattern.getCompanyId().v(), workdayPatternItemDtoList);
    }

}
