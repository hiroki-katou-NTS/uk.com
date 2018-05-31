package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordRepositoty;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class AttendanceRecordItemDeleteCommandHandler
        extends CommandHandler<AttendanceRecordItemDeleteCommand> {

    /**
     * The calculate attendance record repository.
     */
    @Inject
    AttendanceRecordRepositoty attendanceRecordRepositoty;


    /**
     * Handle delete AttendanceRecordItem
     *
     * @param context
     */
    @Override
    protected void handle(CommandHandlerContext<AttendanceRecordItemDeleteCommand> context) {
        AttendanceRecordItemDeleteCommand command = context.getCommand();
        this.attendanceRecordRepositoty.deleteAttendanceRecord(AppContexts.user().companyId(),
                new ExportSettingCode((long) command.getExportSettingCode()));
    }
}
