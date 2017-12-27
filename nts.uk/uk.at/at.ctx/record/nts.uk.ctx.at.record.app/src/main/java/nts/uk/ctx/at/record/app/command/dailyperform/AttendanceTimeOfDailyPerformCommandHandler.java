package nts.uk.ctx.at.record.app.command.dailyperform;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class AttendanceTimeOfDailyPerformCommandHandler extends CommandFacade<AttendanceTimeOfDailyPerformCommand>{

	@Override
	protected void handle(CommandHandlerContext<AttendanceTimeOfDailyPerformCommand> context) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemCommand> T newCommand() {
		return (T) new AttendanceTimeOfDailyPerformCommand();
	}

}
