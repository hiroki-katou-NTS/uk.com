package nts.uk.ctx.at.record.app.command.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
public class DailyRecordWorkCommandHandler extends CommandHandler<DailyRecordWorkCommand>{

	@Inject
	private AttendanceTimeOfDailyPerformCommandHandler attendanceTimeHandler;
	
	@Override
	protected void handle(CommandHandlerContext<DailyRecordWorkCommand> context) {
		DailyRecordWorkCommand command = context.getCommand();
		this.attendanceTimeHandler.handle(command.getAttendanceTimeCommand());
		
	}

}
