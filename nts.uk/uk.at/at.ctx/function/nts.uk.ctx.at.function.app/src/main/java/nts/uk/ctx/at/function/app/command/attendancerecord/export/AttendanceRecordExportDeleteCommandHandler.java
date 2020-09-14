package nts.uk.ctx.at.function.app.command.attendancerecord.export;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;


/**
 * The type AttendanceRecordExportDeleteCommandHandler.
 *
 * @author locph
 */
@Stateless
public class AttendanceRecordExportDeleteCommandHandler extends CommandHandler<AttendanceRecordExportDeleteCommand> {

	/**
	 * The AttendanceRecordExportRepository.
	 */
	@Inject
	AttendanceRecordExportRepository attendanceRecExpRepo;

	/**
	 * Handle delete AttendanceRecordExport
	 *
	 * @param context
	 */
	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordExportDeleteCommand> context) {
		AttendanceRecordExportDeleteCommand command = context.getCommand();
		this.attendanceRecExpRepo.deleteAttendanceRecord(command.getLayoutId());
	}

}
