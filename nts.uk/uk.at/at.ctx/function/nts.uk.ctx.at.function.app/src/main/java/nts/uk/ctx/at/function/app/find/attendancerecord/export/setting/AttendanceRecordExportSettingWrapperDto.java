package nts.uk.ctx.at.function.app.find.attendancerecord.export.setting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AttendanceRecordExportSettingWrapperDto {

	/** The is free setting. */
	private Boolean isFreeSetting;

	/** The free setting lst. */
	private List<AttendanceRecordExportSettingDto> freeSettingLst;

	/** The standard setting lst. */
	private List<AttendanceRecordExportSettingDto> standardSettingLst;

}
