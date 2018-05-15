package nts.uk.ctx.at.function.app.command.attendancerecord.export;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless

public class AttendanceRecordExportSaveCommandHandle extends CommandHandler<AttendanceRecordExportSaveCommand> {

	@Inject
	AttendanceRecordExportRepository attendanceRecExpRepo;

	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordExportSaveCommand> context) {

	}

}
