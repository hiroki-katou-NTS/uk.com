package nts.uk.ctx.at.record.app.command.workrecord.daily;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class DailyWorkRecordCommandHandler extends CommandFacade<DailyWorkRecordCommand>{

	@Override
	protected void handle(CommandHandlerContext<DailyWorkRecordCommand> context) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemCommand> T newCommand() {
		// TODO Auto-generated method stub
		return (T) new DailyWorkRecordCommand();
	}

}
