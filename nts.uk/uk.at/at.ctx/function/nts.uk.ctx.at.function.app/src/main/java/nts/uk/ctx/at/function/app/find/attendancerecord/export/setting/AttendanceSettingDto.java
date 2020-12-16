package nts.uk.ctx.at.function.app.find.attendancerecord.export.setting;

import java.util.List;

import lombok.Data;

@Data
public class AttendanceSettingDto {
	
	/** The attendance record export setting dto. */
	private AttendanceRecordExportSettingDto attendanceRecordExportSettingDto;

	/** The list attendance record export setting. */
	private List<AttendanceRecordExportSettingDto> lstAttendanceRecordExportSetting;
}
