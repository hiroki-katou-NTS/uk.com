package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.app.command.attendancerecord.export.AttendanceRecordExportDeleteCommandHandler;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.AttendanceRecordItemDeleteCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The type DeleteAttendanceRecordExportSettingCommandHandler.
 *
 * @author locph
 */
@Stateless
@Transactional
public class DeleteAttendanceRecordExportSettingCommandHandler extends CommandHandler<DeleteAttendanceRecordExportSettingCommand>{

    /**
     * The AttendanceRecordExportSettingDeleteCommandHandler.
     */
    @Inject
    AttendanceRecordExportSettingDeleteCommandHandler delARESHandler;

    /**
     * The AttendanceRecordExportDeleteCommandHandler.
     */
    @Inject
    AttendanceRecordExportDeleteCommandHandler delAREHandler;

    /**
     * The AttendanceRecordItemDeleteCommandHandler.
     */
    @Inject
    AttendanceRecordItemDeleteCommandHandler delARIHandler;


    /**
     * Handle delete AttendanceRecordExportSetting
     *
     * @param context
     */
    @Override
    protected void handle(CommandHandlerContext<DeleteAttendanceRecordExportSettingCommand> context) {
        DeleteAttendanceRecordExportSettingCommand command = context.getCommand();
        delARESHandler.handle(command.getDelARESCmd());//done
        delAREHandler.handle(command.getDelARECmd());//done
        delARIHandler.handle(command.getDelARICmd());//done
    }
}
