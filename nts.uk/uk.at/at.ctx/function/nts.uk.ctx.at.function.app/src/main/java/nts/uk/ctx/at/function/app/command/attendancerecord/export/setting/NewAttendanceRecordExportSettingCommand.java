package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.AttendanceRecordAddCommand;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.CalculateAttendanceRecordAddCommand;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.SingleAttendanceRecordAddCommand;

/**
 * @author locph
 */
@Setter
@Getter
@AllArgsConstructor

public class NewAttendanceRecordExportSettingCommand {
    private AttendanceRecordExportSettingAddCommand cmd;
    private AttendanceRecordAddCommand itemCmd;
	public NewAttendanceRecordExportSettingCommand() {
		super();
	}
    
}
