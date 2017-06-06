/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtrPK;

/**
 * The Class JpaEmploymentSettingSetMemento.
 */
public class JpaEmploymentSettingSetMemento implements EmploymentSettingSetMemento{

	/** The type value. */
	private KmfmtRetentionEmpCtr typeValue;

	/**
	 * Instantiates a new jpa employment setting set memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaEmploymentSettingSetMemento(KmfmtRetentionEmpCtr typeValue) {
		this.typeValue = typeValue;
		if(this.typeValue.getKmfmtRetentionEmpCtrPK() == null) {
			this.typeValue.setKmfmtRetentionEmpCtrPK(new KmfmtRetentionEmpCtrPK());
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.getKmfmtRetentionEmpCtrPK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingSetMemento#setEmploymentCode(java.lang.String)
	 */
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.typeValue.getKmfmtRetentionEmpCtrPK().setEmpCtrCd(employmentCode);
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingSetMemento#setUpperLimitSetting(nts.uk.ctx.at.shared.dom
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
	 * EmploymentSettingSetMemento#setManagementCategory(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.retentionyearly.ManagementCategory)
	 */
	@Override
	public void setManagementCategory(ManagementCategory managementCategory) {
		this.typeValue.setManagementCtrAtr((short) managementCategory.value);
	}

}
