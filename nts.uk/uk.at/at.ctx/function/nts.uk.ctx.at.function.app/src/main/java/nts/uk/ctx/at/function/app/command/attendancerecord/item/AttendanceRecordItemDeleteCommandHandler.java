package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordRepositoty;

/**
 * The type AttendanceRecordItemDeleteCommandHandler.
 *
 * @author locph
 */
@Stateless
public class AttendanceRecordItemDeleteCommandHandler
        extends CommandHandler<AttendanceRecordItemDeleteCommand> {

    /**
     * The AttendanceRecordRepositoty.
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
        this.attendanceRecordRepositoty.deleteAttendanceRecord(command.getLayoutId());
    }
}
