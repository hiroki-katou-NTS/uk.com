package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyEventCommonCommand;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

@Getter
public class UpdateBreakTimeByTimeLeaveChangeCommand extends DailyEventCommonCommand {

	List<TimeLeavingWork> timeLeave = new ArrayList<>();
	
	WorkTimeCode newWorkTimeCode;

	Optional<BreakTimeOfDailyPerformance> cachedBreackTime = Optional.empty();
	
	
	public static UpdateBreakTimeByTimeLeaveChangeCommand builder() {
		return new UpdateBreakTimeByTimeLeaveChangeCommand();
	}
	public UpdateBreakTimeByTimeLeaveChangeCommand timeLeave(List<TimeLeavingWork> timeLeave) {
		this.timeLeave = timeLeave;
		return this;
	}
	
	public UpdateBreakTimeByTimeLeaveChangeCommand newWorkTimeCode(WorkTimeCode newWorkTimeCode) {
		this.newWorkTimeCode = newWorkTimeCode;
		return this;
	}
	
	public UpdateBreakTimeByTimeLeaveChangeCommand cachedBreakTime(BreakTimeOfDailyPerformance cachedBreackTime) {
		this.cachedBreackTime = Optional.ofNullable(cachedBreackTime);
		return this;
	}
}
