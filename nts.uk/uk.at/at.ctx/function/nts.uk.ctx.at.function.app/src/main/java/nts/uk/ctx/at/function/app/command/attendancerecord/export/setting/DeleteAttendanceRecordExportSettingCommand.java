package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.app.command.attendancerecord.export.AttendanceRecordExportDeleteCommand;
import nts.uk.ctx.at.function.app.command.attendancerecord.item.AttendanceRecordItemDeleteCommand;

/**
 * The Class DeleteAttendanceRecordExportSettingCommand.
 *
 * @author locph
 */
@Getter
@Setter
@AllArgsConstructor
public class DeleteAttendanceRecordExportSettingCommand {
	private AttendanceRecordExportSettingDeleteCommand delARESCmd;
	private AttendanceRecordExportDeleteCommand delARECmd;
	private AttendanceRecordItemDeleteCommand delARICmd;
}
