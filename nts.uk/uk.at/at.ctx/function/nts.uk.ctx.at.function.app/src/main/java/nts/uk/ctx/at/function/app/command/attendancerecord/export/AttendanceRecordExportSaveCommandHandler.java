package nts.uk.ctx.at.function.app.command.attendancerecord.export;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless

public class AttendanceRecordExportSaveCommandHandler extends CommandHandler<AttendanceRecordExportSaveCommand> {

	@Inject
	AttendanceRecordExportRepository attendanceRecExpRepo;

	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordExportSaveCommand> context) {

	}

}
