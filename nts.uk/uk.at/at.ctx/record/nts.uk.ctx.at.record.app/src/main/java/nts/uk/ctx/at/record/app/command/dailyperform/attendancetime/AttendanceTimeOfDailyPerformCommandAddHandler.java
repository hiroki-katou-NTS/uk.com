package nts.uk.ctx.at.record.app.command.dailyperform.attendancetime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class AttendanceTimeOfDailyPerformCommandAddHandler extends CommandFacade<AttendanceTimeOfDailyPerformCommand> {

	@Inject
	private AttendanceTimeRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AttendanceTimeOfDailyPerformCommand> context) {
		AttendanceTimeOfDailyPerformCommand command = context.getCommand();
		if(command.getData().isPresent()){
			repo.add(command.toDomain().get());
		}
	}

}
