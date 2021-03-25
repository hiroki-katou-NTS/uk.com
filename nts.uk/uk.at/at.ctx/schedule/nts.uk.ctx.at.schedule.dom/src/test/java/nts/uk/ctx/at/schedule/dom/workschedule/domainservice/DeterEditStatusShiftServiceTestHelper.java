package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;

public class DeterEditStatusShiftServiceTestHelper {
	
	public static WorkSchedule createWithEditStateList(List<EditStateOfDailyAttd> lstEditState) {
		return new WorkSchedule("employeeID",
				GeneralDate.today(), 
				ConfirmedATR.CONFIRMED, 
				null, 
				null, 
				new BreakTimeOfDailyAttd(),
				lstEditState,
				TaskSchedule.createWithEmptyList(),
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(),
				Optional.empty());
	}

}
