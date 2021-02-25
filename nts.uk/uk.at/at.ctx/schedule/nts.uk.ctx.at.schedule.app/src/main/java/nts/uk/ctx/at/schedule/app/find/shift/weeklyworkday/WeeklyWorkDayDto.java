package nts.uk.ctx.at.schedule.app.find.shift.weeklyworkday;

import lombok.*;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WeeklyWorkDayPattern;

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

    /**
     * CompanyId
     */
    private String companyId;

    /**
     * work pattern item list
     */
    private List<WorkdayPatternItemDto> workdayPatternItemDtoList;

    public WeeklyWorkDayDto toDto(WeeklyWorkDayPattern weeklyWorkDayPattern) {
        if(weeklyWorkDayPattern != null) {
            List<WorkdayPatternItemDto> workdayPatternItemDtoList = weeklyWorkDayPattern.getListWorkdayPatternItem()
                    .stream()
                    .map(item ->
                            new WorkdayPatternItemDto(item.getDayOfWeek().value, item.getWorkdayDivision().value)
                    ).collect(Collectors.toList());
            return new WeeklyWorkDayDto(weeklyWorkDayPattern.getCompanyId().v(), workdayPatternItemDtoList);
        }
        return new WeeklyWorkDayDto();
    }

}
