package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkTimeResultDto {
    private Integer disabledSegment;
    private List<WorkTimeSetting> allWorkHours;
    private List<WorkTimeSetting> workingHoursByWorkplace;
    private List<WorkTimeSetting> availableWorkingHours;
}
