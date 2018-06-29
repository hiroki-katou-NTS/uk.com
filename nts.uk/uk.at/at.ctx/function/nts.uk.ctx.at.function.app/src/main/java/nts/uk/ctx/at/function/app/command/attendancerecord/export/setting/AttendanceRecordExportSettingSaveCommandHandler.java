package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceRecordExportSettingSaveCommandHandler.
 */
@Stateless
public class AttendanceRecordExportSettingSaveCommandHandler
		extends CommandHandler<AttendanceRecordExportSettingSaveCommand> {

	/** The attendance rec exp set repo. */
	@Inject
	AttendanceRecordExportSettingRepository attendanceRecExpSetRepo;

	/**
	 * Handle.
	 *
	 * @param context the context
	 */
	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordExportSettingSaveCommand> context) {
		AttendanceRecordExportSettingSaveCommand command = context.getCommand();

		// convert to domain

		AttendanceRecordExportSetting domain = new AttendanceRecordExportSetting();
		domain.setCompanyId(AppContexts.user().companyId());
		domain.setCode(new ExportSettingCode(command.getCode()));
		domain.setName(new ExportSettingName(command.getName()));
		domain.setSealStamp(
				command.getSealStamp().stream().map(item -> new SealColumnName(item)).collect(Collectors.toList()));
		domain.setSealUseAtr(command.getSealUseAtr());

		// update
		attendanceRecExpSetRepo.updateAttendanceRecExpSet(domain);
	}

}
