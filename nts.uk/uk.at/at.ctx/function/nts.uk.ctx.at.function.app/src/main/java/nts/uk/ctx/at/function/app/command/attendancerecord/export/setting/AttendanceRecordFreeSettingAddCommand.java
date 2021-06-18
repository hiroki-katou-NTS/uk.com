package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;

/**
 * The Class AttendanceRecordExportSettingAddCommand.
 */
@Data
@AllArgsConstructor
public class AttendanceRecordFreeSettingAddCommand
		implements AttendanceRecordFreeSetting.MementoGetter {

	private String cid;
	
	private String employeeId;
	
	private int itemSelectionType;
	
	private List<AttendanceRecordExportSettingAddCommand> attendanceRecordExportSettings;

	@Override
	public List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings() {
		return this.attendanceRecordExportSettings.stream()
				.map(t -> new AttendanceRecordExportSetting(t))
				.collect(Collectors.toList());
	}

}
