package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WorkdayPatternItem;

import java.util.List;

@Getter
@AllArgsConstructor
public class WeeklyWorkDto {

    private List<WorkdayPatternItem> weeklyWorkDayPatternDtos;

    private List<String> workTypeName;

    private String workTimeName;

}
