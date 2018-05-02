package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

@Getter
@Builder
public class UpdateBreakTimeByTimeLeaveChangeCommand {

	private String employeeId;
	
	private GeneralDate workingDate;
	
	private List<TimeLeavingWork> timeLeave;
	
	private WorkTimeCode newWorkTimeCode;
}
