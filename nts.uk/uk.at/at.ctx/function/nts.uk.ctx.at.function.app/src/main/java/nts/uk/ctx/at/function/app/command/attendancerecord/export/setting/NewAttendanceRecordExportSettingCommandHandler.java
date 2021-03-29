package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.AttendanceRecordAddCommandHandler;


/**
 * The type NewAttendanceRecordExportSettingCommandHandler.
 *
 * @author locph
 */
@Stateless
@Transactional
public class NewAttendanceRecordExportSettingCommandHandler
		extends CommandHandler<NewAttendanceRecordExportSettingCommand> {

    /**
     * The AttendanceRecordExportSettingAddCommandHandler.
     */
    @Inject
    AttendanceRecordExportSettingAddCommandHandler cmdHandler;

    /**
     * The AttendanceRecordAddCommandHandler.
     */
    @Inject
    AttendanceRecordAddCommandHandler itemCmdHandler;

    /**
     * Handle add/update AttendanceRecordExportSetting
     *
     * @param context
     */
    @Override
    protected void handle(CommandHandlerContext<NewAttendanceRecordExportSettingCommand> context) {
		NewAttendanceRecordExportSettingCommand command = context.getCommand();
		String layoutId = command.getCmd().getLayoutId() == null ? UUID.randomUUID().toString() : command.getCmd().getLayoutId();
		command.getCmd().setOnceUpdate(command.isOnceUpdate());
		command.getCmd().setLayoutId(layoutId);
		command.getItemCmd().setLayoutId(layoutId);
		cmdHandler.handle(command.getCmd());
		if (!command.isOnceUpdate())
			itemCmdHandler.handle(command.getItemCmd());
    }
}
