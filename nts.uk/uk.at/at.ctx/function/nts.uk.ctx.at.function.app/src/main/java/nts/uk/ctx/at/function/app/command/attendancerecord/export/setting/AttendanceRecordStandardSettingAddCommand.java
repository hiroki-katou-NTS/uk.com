package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;

/**
 * The Class AttendanceRecordStandardSettingAddCommand.
 * @author LienPTK
 */
@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordStandardSettingAddCommand implements AttendanceRecordStandardSetting.MementoGetter {

	private String cid;

	private int itemSelectionType;

	private List<AttendanceRecordExportSettingAddCommand> attendanceRecordExportSettings;

	@Override
	public List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings() {
		return this.attendanceRecordExportSettings.stream()
				.map(t -> new AttendanceRecordExportSetting(t))
				.collect(Collectors.toList());
	}

}
