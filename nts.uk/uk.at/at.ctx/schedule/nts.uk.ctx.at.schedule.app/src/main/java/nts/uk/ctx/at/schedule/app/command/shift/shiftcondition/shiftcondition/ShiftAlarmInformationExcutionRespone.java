package nts.uk.ctx.at.schedule.app.command.shift.shiftcondition.shiftcondition;

import lombok.Getter;
import lombok.Setter;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftAlarmInformation;

@Getter
@Setter
public class ShiftAlarmInformationExcutionRespone {
	/** The task infor. */
	public AsyncTaskInfo taskInfor;

	public ShiftAlarmInformation shifAlarmInformation;

}
