package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.service.SelectWorkOutput;

@AllArgsConstructor
@NoArgsConstructor
public class SelectWorkOutputDto {
	// 勤務時間
	public WorkHoursDto workHours;
	// 休憩時間帯設定
	public BreakTimeZoneSettingDto breakTimeZoneSetting;
	// 申請時間
	public ApplicationTimeDto applicationTime;
	
	public static SelectWorkOutputDto fromDomain(SelectWorkOutput selectWorkOutput) {
		return new SelectWorkOutputDto(
				WorkHoursDto.fromDomain(selectWorkOutput.getWorkHours()),
				BreakTimeZoneSettingDto.fromDomain(selectWorkOutput.getBreakTimeZoneSetting()),
				ApplicationTimeDto.fromDomain(selectWorkOutput.getApplicationTime()));
	}
}
