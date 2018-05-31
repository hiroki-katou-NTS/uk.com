package nts.uk.ctx.at.function.app.command.attendancerecord.export;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 * @author locph
 */
@Stateless
public class AttendanceRecordExportDeleteCommandHandler extends CommandHandler<AttendanceRecordExportDeleteCommand> {

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
		this.attendanceRecExpRepo.deleteAttendanceRecord(AppContexts.user().companyId(),
				new ExportSettingCode(command.getExportSettingCode()));
	}

}
