package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
@Builder
public class UpdateBreakTimeByTimeLeaveChangeCommand {

	@Builder.Default
	boolean isTriggerRelatedEvent = true;
	
	String employeeId;
	
	GeneralDate workingDate;

	@Builder.Default
	List<TimeLeavingWork> timeLeave = new ArrayList<>();
	
	WorkTimeCode newWorkTimeCode;
	
	@Builder.Default
	Optional<String> companyId = Optional.empty();

	@Builder.Default
	Optional<WorkInfoOfDailyPerformance> cachedWorkInfo = Optional.empty();

	@Builder.Default
	Optional<WorkType> cachedWorkType = Optional.empty();

	@Builder.Default
	Optional<TimeLeavingOfDailyPerformance> cachedTimeLeave  = Optional.empty();

	@Builder.Default
	Optional<List<EditStateOfDailyPerformance>> cachedEditState = Optional.empty();

	@Builder.Default
	Optional<BreakTimeOfDailyPerformance> cachedBreackTime = Optional.empty();
	
	@Builder.Default
	boolean actionOnCache = false;
}
