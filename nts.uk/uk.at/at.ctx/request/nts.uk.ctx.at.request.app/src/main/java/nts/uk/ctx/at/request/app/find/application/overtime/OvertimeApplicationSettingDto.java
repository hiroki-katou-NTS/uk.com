package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;

@AllArgsConstructor
@NoArgsConstructor
public class OvertimeApplicationSettingDto {
	// frameNo
	public Integer frameNo;
	// type
	public Integer attendanceType;
	// 申請時間
	public Integer applicationTime;
	
	public static OvertimeApplicationSettingDto fromDomain(OvertimeApplicationSetting overtimeApplicationSetting) {
		return new OvertimeApplicationSettingDto(
				overtimeApplicationSetting.getFrameNo().v(),
				overtimeApplicationSetting.getAttendanceType().value,
				overtimeApplicationSetting.getApplicationTime().v());
	}
}
