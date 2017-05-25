package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

@Getter
@Setter
public class RetentionYearlyFindDto implements RetentionYearlySettingSetMemento{
	
	private String companyId;
	private UpperLimitSettingFindDto upperLimitSetting;
	

	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public void setUpperLimitSetting(UpperLimitSetting upperLimitSetting) {
		this.upperLimitSetting = new UpperLimitSettingFindDto();
		this.upperLimitSetting.setMaxDaysCumulation(upperLimitSetting.getMaxDaysCumulation().v());
		this.upperLimitSetting.setRetentionYearsAmount(upperLimitSetting.getRetentionYearsAmount().v());
	}

	@Override
	public void setcanAddToCumulationYearlyAsNormalWorkDay(Boolean canAddToCumulationYearlyAsNormalWorkDay) {
		// TODO Auto-generated method stub
		
	}

}
