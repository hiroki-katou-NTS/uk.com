/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;

/**
 * The Class JpaEmploymentSettingGetMemento.
 */
public class JpaEmploymentSettingGetMemento implements EmploymentSettingGetMemento{
	
	/** The type value. */
	private KmfmtRetentionEmpCtr typeValue;
	
	/**
	 * Instantiates a new jpa employment setting get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaEmploymentSettingGetMemento(KmfmtRetentionEmpCtr typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typeValue.getKmfmtRetentionEmpCtrPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingGetMemento#getEmploymentCode()
	 */
	@Override
	public String getEmploymentCode() {
		return this.typeValue.getKmfmtRetentionEmpCtrPK().getEmpCtrCd();
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingGetMemento#getUpperLimitSetting()
	 */
	@Override
	public UpperLimitSetting getUpperLimitSetting() {
		return new UpperLimitSetting(new JpaUpperLimitEmpGetMemento(this.typeValue));
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmploymentSettingGetMemento#getManagementCategory()
	 */
	@Override
	public ManagementCategory getManagementCategory() {
		return ManagementCategory.valueOf((int)this.typeValue.getManagementCtrAtr());
	}

}
