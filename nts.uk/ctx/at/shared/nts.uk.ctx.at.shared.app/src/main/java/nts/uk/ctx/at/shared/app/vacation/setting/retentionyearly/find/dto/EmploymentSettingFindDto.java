package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

@Getter
@Setter
public class EmploymentSettingFindDto implements EmploymentSettingSetMemento{
	
	private String companyId;
	private UpperLimitSettingFindDto upperLimitSetting;
	private String employmentCode;
	private Integer managementCategory;
	
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
		
	}

	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
		
	}

	@Override
	public void setUpperLimitSetting(UpperLimitSetting upperLimitSetting) {
		this.upperLimitSetting = new UpperLimitSettingFindDto();
		this.upperLimitSetting.setMaxDaysCumulation(upperLimitSetting.getMaxDaysCumulation().v());
		this.upperLimitSetting.setRetentionYearsAmount(upperLimitSetting.getRetentionYearsAmount().v());
	}

	@Override
	public void setManagementCategory(ManagementCategory managementCategory) {
		this.managementCategory = managementCategory.value;
	}
	
}
