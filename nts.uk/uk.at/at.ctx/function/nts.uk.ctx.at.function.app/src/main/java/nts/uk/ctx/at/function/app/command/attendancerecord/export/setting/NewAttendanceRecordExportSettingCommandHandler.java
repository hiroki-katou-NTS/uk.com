package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.AttendanceRecordAddCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author locph
 */
@Stateless
@Transactional
public class NewAttendanceRecordExportSettingCommandHandler extends CommandHandler<NewAttendanceRecordExportSettingCommand>{

    @Inject
    AttendanceRecordExportSettingAddCommandHandler cmdHandler;

    @Inject
    AttendanceRecordAddCommandHandler itemCmdHandler;

    @Override
    protected void handle(CommandHandlerContext<NewAttendanceRecordExportSettingCommand> context) {
        NewAttendanceRecordExportSettingCommand command = context.getCommand();
        cmdHandler.handle(command.getCmd());//done
        itemCmdHandler.handle(command.getItemCmd());//done
    }
}
