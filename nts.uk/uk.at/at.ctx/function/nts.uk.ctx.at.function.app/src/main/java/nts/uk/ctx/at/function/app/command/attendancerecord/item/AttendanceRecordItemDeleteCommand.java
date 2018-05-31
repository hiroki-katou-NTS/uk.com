package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author locph
 */
@AllArgsConstructor
@Getter
@Setter
public class AttendanceRecordItemDeleteCommand {

    /** The export setting code. */
    protected int exportSettingCode;

}
