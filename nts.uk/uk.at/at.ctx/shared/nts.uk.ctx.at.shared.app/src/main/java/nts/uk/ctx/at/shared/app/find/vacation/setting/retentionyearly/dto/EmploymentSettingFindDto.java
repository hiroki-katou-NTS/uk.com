/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.retentionyearly.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;


/**
 * The Class EmploymentSettingFindDto.
 */
@Getter
@Setter
public class EmploymentSettingFindDto implements EmptYearlyRetentionSetMemento {
	
	/** The company id. */
	private String companyId;
	
	/** The upper limit setting. */
	private UpperLimitSettingFindDto upperLimitSetting;
	
	/** The employment code. */
	private String employmentCode;
	
	/** The management category. */
	private Integer managementCategory;

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionSetMemento#setEmploymentCode(java.lang.String)
	 */
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}


	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionSetMemento#setManagementCategory(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.retentionyearly.ManagementCategory)
	 */
	@Override
	public void setManagementCategory(ManageDistinct managementCategory) {
		this.managementCategory = managementCategory.value;
	}
	
}
