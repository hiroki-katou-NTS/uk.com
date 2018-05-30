package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class AttendanceRecordAddCommandHandler extends CommandHandler<AttendanceRecordAddCommand>{

	@Inject
	SingleAttendanceRecordAddCommandHandler singleHandler;

	@Inject
	CalculateAttendanceRecordAddCommandHandler calculateHandler;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordAddCommand> context) {
		AttendanceRecordAddCommand command = context.getCommand();
		command.getSingleList().forEach(cmd -> singleHandler.handle(cmd));
		command.getCalculateList().forEach(cmd -> calculateHandler.handle(cmd));
	}

}
