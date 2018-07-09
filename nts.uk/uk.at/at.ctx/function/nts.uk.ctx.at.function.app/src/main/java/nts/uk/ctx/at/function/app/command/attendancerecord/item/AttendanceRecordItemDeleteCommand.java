package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type AttendanceRecordItemDeleteCommand.
 *
 * @author locph
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttendanceRecordItemDeleteCommand {

    /**
     * The export setting code.
     */
    protected int exportSettingCode;

}
