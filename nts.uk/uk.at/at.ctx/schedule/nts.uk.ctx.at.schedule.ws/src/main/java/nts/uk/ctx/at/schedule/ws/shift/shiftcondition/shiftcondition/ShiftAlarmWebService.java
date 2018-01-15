package nts.uk.ctx.at.schedule.ws.shift.shiftcondition.shiftcondition;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.schedule.app.command.shift.shiftcondition.shiftcondition.ShiftAlarmInformationAddCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.shiftcondition.shiftcondition.ShiftAlarmInformationCommand;
import nts.uk.ctx.at.schedule.app.command.shift.shiftcondition.shiftcondition.ShiftAlarmInformationExcutionRespone;

@Path("at/schedule/shift/shiftCondition/shiftAlarm")
@Produces("application/json")
public class ShiftAlarmWebService extends WebService{
	@Inject
	ShiftAlarmInformationAddCommandHandler alarmCommandHandler;

	@POST 
	@Path("execution")
	public ShiftAlarmInformationExcutionRespone process(ShiftAlarmInformationCommand command) {
		AsyncTaskInfo taskInfor = this.alarmCommandHandler.handle(command);
		ShiftAlarmInformationExcutionRespone respone = new ShiftAlarmInformationExcutionRespone();
		respone.setTaskInfor(taskInfor);
		return respone;

	}
	
}
