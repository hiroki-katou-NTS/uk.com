package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AttendanceRecordItemDeleteCommand {

    /** The export setting code. */
    protected int exportSettingCode;

}
