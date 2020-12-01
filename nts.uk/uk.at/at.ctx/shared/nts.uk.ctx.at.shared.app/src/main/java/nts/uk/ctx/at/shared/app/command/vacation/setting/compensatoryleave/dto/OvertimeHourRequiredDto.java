package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OvertimeHourRequired;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OvertimeHourRequiredDto {
	
		//使用区分 
		private boolean UseAtr;
		//時間設定
		private TimeSettingDto timeSetting;
		
		public OvertimeHourRequired toDomain() {
			return new OvertimeHourRequired(UseAtr, timeSetting.toDomain());
		}
		
		public static OvertimeHourRequiredDto toDto(OvertimeHourRequired domain) {
			return new OvertimeHourRequiredDto(domain.isUseAtr(), TimeSettingDto.toDto(domain.getTimeSetting()));
		}

}
