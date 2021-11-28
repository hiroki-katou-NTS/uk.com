package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.MultipleOvertimeContentDto;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFindDto;

import java.util.List;

@Value
public class MultiOvertimWithWorkDayOffDto {
    private AppOverTimeDto latestMultiOvertimeApp;
    private List<WorkdayoffFrameFindDto> dayOffWorkFrames;
}
