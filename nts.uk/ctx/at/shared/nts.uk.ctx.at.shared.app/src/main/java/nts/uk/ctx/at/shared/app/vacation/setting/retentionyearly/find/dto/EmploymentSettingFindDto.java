/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * Gets the management category.
 *
 * @return the management category
 */
@Getter
@Setter
public class EmploymentSettingFindDto implements EmploymentSettingSetMemento{
	
	/** The company id. */
	private String companyId;
	
	/** The upper limit setting. */
	private UpperLimitSettingFindDto upperLimitSetting;
	
	/** The employment code. */
	private String employmentCode;
	
	/** The management category. */
	private Integer managementCategory;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingSetMemento#setEmploymentCode(java.lang.String)
	 */
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingSetMemento#setUpperLimitSetting(nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting)
	 */
	@Override
	public void setUpperLimitSetting(UpperLimitSetting upperLimitSetting) {
		this.upperLimitSetting = new UpperLimitSettingFindDto();
		this.upperLimitSetting.setMaxDaysCumulation(upperLimitSetting.getMaxDaysCumulation().v());
		this.upperLimitSetting.setRetentionYearsAmount(upperLimitSetting.getRetentionYearsAmount().v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingSetMemento#setManagementCategory(nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory)
	 */
	@Override
	public void setManagementCategory(ManagementCategory managementCategory) {
		this.managementCategory = managementCategory.value;
	}
	
}
