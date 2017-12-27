package nts.uk.ctx.at.record.app.command.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AddAttendanceTimeOfDailyPerformCommandHandler;

@Stateless
public class DailyRecordWorkCommandHandler extends CommandHandler<DailyRecordWorkCommand>{

	@Inject
	private AddAttendanceTimeOfDailyPerformCommandHandler attendanceTimeHandler;
	
	@Override
	protected void handle(CommandHandlerContext<DailyRecordWorkCommand> context) {
		DailyRecordWorkCommand command = context.getCommand();
		this.attendanceTimeHandler.handle(command.getAttendanceTimeCommand());
	}

}
