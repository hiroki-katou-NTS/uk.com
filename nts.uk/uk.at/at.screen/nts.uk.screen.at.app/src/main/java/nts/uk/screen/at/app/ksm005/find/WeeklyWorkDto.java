package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WorkdayPatternItem;

import java.util.List;

@Getter
@AllArgsConstructor
public class WeeklyWorkDto {

    private List<WorkdayPatternDto> weeklyWorkDayPatternDtos;

    private List<workTypeDto> workType;

    private List<String> workTypeCode;

    private String workTimeName;

}
