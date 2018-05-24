package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.CalculateAttendanceRecordAddCommandHandler;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.SingleAttendanceRecordAddCommandHandler;

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
    AttendanceRecordExportSettingAddCommandHandler aRESHandler;

    @Inject
    SingleAttendanceRecordAddCommandHandler sARHandler;

    @Inject
    CalculateAttendanceRecordAddCommandHandler cARHandler;


    @Override
    protected void handle(CommandHandlerContext<NewAttendanceRecordExportSettingCommand> context) {
        NewAttendanceRecordExportSettingCommand command = context.getCommand();
        aRESHandler.handle(command.getARESCommand());//done
        sARHandler.handle(command.getSARCommand());//done
        cARHandler.handle(command.getCARCommand());//done
    }
}
