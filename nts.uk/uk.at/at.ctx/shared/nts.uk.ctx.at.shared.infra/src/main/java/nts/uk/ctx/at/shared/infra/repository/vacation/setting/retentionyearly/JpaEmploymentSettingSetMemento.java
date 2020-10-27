/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmpPK;

/**
 * The Class JpaEmploymentSettingSetMemento.
 */
public class JpaEmploymentSettingSetMemento implements EmptYearlyRetentionSetMemento {

	/** The type value. */
	private KshmtHdstkSetEmp typeValue;

	/**
	 * Instantiates a new jpa employment setting set memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaEmploymentSettingSetMemento(KshmtHdstkSetEmp typeValue) {
		this.typeValue = typeValue;
		if(this.typeValue.getKshmtHdstkSetEmpPK() == null) {
			this.typeValue.setKshmtHdstkSetEmpPK(new KshmtHdstkSetEmpPK());
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.getKshmtHdstkSetEmpPK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionSetMemento#setEmploymentCode(java.lang.String)
	 */
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.typeValue.getKshmtHdstkSetEmpPK().setEmpCtrCd(employmentCode);
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionSetMemento#setUpperLimitSetting(nts.uk.ctx.at.shared.dom
	 * .vacation.setting.retentionyearly.UpperLimitSetting)
	 */
	@Override
	public void setUpperLimitSetting(UpperLimitSetting upperLimitSetting) {
		this.typeValue.setMaxDaysRetention(upperLimitSetting.getMaxDaysCumulation().v().shortValue());
		this.typeValue.setYearAmount(upperLimitSetting.getRetentionYearsAmount().v().shortValue());
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionSetMemento#setManagementCategory(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.retentionyearly.ManagementCategory)
	 */
	@Override
	public void setManagementCategory(ManageDistinct managementCategory) {
		this.typeValue.setManagementCtrAtr((short) managementCategory.value);
	}

}
