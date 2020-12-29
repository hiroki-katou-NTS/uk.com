package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.HolidayWorkHourRequired;
/** 代休発生に必要な休日出勤時間 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HolidayWorkHourRequiredDto {
	//使用区分 
	private boolean UseAtr;
	//時間設定
	private TimeSettingDto timeSetting;
	
	public HolidayWorkHourRequired toDomain() {
		return new HolidayWorkHourRequired(UseAtr, timeSetting.toDomain());
	}
	
	public static HolidayWorkHourRequiredDto toDto(HolidayWorkHourRequired domain) {
		return new HolidayWorkHourRequiredDto(domain.isUseAtr(), TimeSettingDto.toDto(domain.getTimeSetting()));
	}
}
