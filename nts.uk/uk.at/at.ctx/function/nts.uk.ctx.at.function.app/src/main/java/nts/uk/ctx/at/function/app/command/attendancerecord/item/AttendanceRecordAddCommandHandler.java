package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The type AttendanceRecordAddCommandHandler.
 *
 * @author locph
 */
@Stateless
public class AttendanceRecordAddCommandHandler extends CommandHandler<AttendanceRecordAddCommand>{

	/**
	 * The SingleAttendanceRecordAddCommandHandler.
	 */
	@Inject
	SingleAttendanceRecordAddCommandHandler singleHandler;

	/**
	 * The CalculateAttendanceRecordAddCommandHandler.
	 */
	@Inject
	CalculateAttendanceRecordAddCommandHandler calculateHandler;

	/* (non-Javadoc)
	 * Handle add list AttendanceRecord
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 *
	 */
	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordAddCommand> context) {
		AttendanceRecordAddCommand command = context.getCommand();
		command.getSingleList().forEach(cmd -> singleHandler.handle(cmd));
		command.getCalculateList().forEach(cmd -> calculateHandler.handle(cmd));
	}

}
