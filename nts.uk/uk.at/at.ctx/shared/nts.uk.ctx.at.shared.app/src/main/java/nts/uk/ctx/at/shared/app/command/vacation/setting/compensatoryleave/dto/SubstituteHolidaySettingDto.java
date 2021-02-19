package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.SubstituteHolidaySetting;

/** 代休発生設定 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubstituteHolidaySettingDto {

	//代休発生に必要な休日出勤時間 
	private HolidayWorkHourRequiredDto holidayWorkHourRequired;
	
	//代休発生に必要な残業時間
	private OvertimeHourRequiredDto overtimeHourRequired;
	
	public SubstituteHolidaySetting toDomain() {
		return new SubstituteHolidaySetting(
				holidayWorkHourRequired.toDomain(),
				overtimeHourRequired.toDomain());
	}
	
	public static SubstituteHolidaySettingDto toDto(SubstituteHolidaySetting domain) {
		return new SubstituteHolidaySettingDto(
				HolidayWorkHourRequiredDto.toDto(domain.getHolidayWorkHourRequired()),
				OvertimeHourRequiredDto.toDto(domain.getOvertimeHourRequired()));
	}
	
}



