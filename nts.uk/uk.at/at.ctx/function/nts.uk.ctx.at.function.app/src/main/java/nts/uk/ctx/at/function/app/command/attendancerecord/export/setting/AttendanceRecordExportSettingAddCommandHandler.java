package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.Arrays;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceRecordExportSettingAddCommandHandler.
 */
@Stateless
public class AttendanceRecordExportSettingAddCommandHandler
		extends CommandHandler<AttendanceRecordExportSettingAddCommand> {
	
	/** The free setting repo. */
	@Inject
	AttendanceRecordFreeSettingRepository freeSettingRepo;
	
	/** The standard setting repo. */
	@Inject
	AttendanceRecordStandardSettingRepository standardSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordExportSettingAddCommand> context) {
		String companyId = AppContexts.user().companyId();
		AttendanceRecordExportSettingAddCommand command = context.getCommand();

		if (command.itemSelType == ItemSelectionType.FREE_SETTING.value) {
			String employeeId = AppContexts.user().employeeId();
			AttendanceRecordFreeSettingAddCommand addCommamd = new AttendanceRecordFreeSettingAddCommand(companyId
					, employeeId
					, ItemSelectionType.FREE_SETTING.value
					, Arrays.asList(command));
			this.freeSettingRepo.save(AttendanceRecordFreeSetting.createFromMemento(addCommamd));
		}
		
		if (command.itemSelType == ItemSelectionType.STANDARD_SETTING.value) {
			AttendanceRecordStandardSettingAddCommand addCommamd = new AttendanceRecordStandardSettingAddCommand(companyId
					, ItemSelectionType.STANDARD_SETTING.value
					, Arrays.asList(command));
			this.standardSettingRepo.save(AttendanceRecordStandardSetting.createFromMemento(addCommamd));
		}
	}

}
