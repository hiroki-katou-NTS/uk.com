package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

@AllArgsConstructor
@Setter
@Getter
public class SearchStartTimeFinder {
    private GeneralDate startTime;
    private GeneralDate endTime;
    private WorkTimeSetting workTimeSetting;
}
