package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * The Class AttendanceRecordExportSettingDeleteCommandHandler.
 */
@Stateless
public class AttendanceRecordExportSettingDeleteCommandHandler
		extends CommandHandler<AttendanceRecordExportSettingDeleteCommand> {

	/** The attendance rec exp set repo. */
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
	protected void handle(CommandHandlerContext<AttendanceRecordExportSettingDeleteCommand> context) {

		AttendanceRecordExportSettingDeleteCommand command = context.getCommand();

		// convert to domain
		AttendanceRecordExportSetting domain = new AttendanceRecordExportSetting();
		domain.setCompanyId(AppContexts.user().companyId());
		domain.setCode(new ExportSettingCode(command.getCode()));
		domain.setName(new ExportSettingName(command.getName()));
		domain.setSealStamp(command.getSealStamp().stream().map(SealColumnName::new).collect(Collectors.toList()));

		// Delete
		attendanceRecExpSetRepo.deleteAttendanceRecExpSet(domain);
	}

}
