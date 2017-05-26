package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

@Getter
@Setter
public class RetentionYearlyDto {
	
	private UpperLimitSettingDto upperLimitSettingDto;

	
	public RetentionYearlySetting toDomain(String companyId) {
		return new RetentionYearlySetting(new GetMementoImpl(companyId, this));
	}
	
	
	private class GetMementoImpl implements RetentionYearlySettingGetMemento {
		
		private RetentionYearlyDto dto;
		private String companyId;
		
		public GetMementoImpl(String companyId, RetentionYearlyDto dto) {
			super();
			this.companyId = companyId;
			this.dto = dto;
		}
		
		@Override
		public String getCompanyId() {
			return this.companyId;
		}
		@Override
		public UpperLimitSetting getUpperLimitSetting() {
			return dto.upperLimitSettingDto.toDomain();
		}
		@Override
		public Boolean getCanAddToCumulationYearlyAsNormalWorkDay() {
//			return dto.canAddToCumulationYearlyAsNormalWorkDay;
			return null;
		}
		
	}
	

}
