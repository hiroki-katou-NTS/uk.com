package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.*;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceRecordExportSettingAddCommandHandler.
 */
@Stateless
public class AttendanceRecordExportSettingAddCommandHandler
		extends CommandHandler<AttendanceRecordExportSettingAddCommand> {

	/** The Attendance rec exp set repo. */
	@Inject
	AttendanceRecordExportSettingRepository attendanceRecExpSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordExportSettingAddCommand> context) {

		AttendanceRecordExportSettingAddCommand command = context.getCommand();

		// convert to domain

		AttendanceRecordExportSetting domain = new AttendanceRecordExportSetting();
		domain.setCompanyId(AppContexts.user().companyId());
		domain.setCode(new ExportSettingCode(command.getCode()));
		domain.setName(new ExportSettingName(command.getName()));
		domain.setNameUseAtr(NameUseAtr.valueOf(command.getNameUseAtr()));
		if (!command.onceUpdate) {
			domain.setSealStamp(command.getSealStamp().stream().map(SealColumnName::new).collect(Collectors.toList()));
			domain.setSealUseAtr(command.getSealUseAtr());
		}
		// Add
		attendanceRecExpSetRepo.addAttendanceRecExpSet(domain,command.isOnceUpdate());
	}

}
