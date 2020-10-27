/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KshmtHdstkSetEmp;

/**
 * The Class JpaEmploymentSettingGetMemento.
 */
public class JpaEmploymentSettingGetMemento implements EmptYearlyRetentionGetMemento {
	
	/** The type value. */
	private KshmtHdstkSetEmp typeValue;
	
	/**
	 * Instantiates a new jpa employment setting get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaEmploymentSettingGetMemento(KshmtHdstkSetEmp typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typeValue.getKshmtHdstkSetEmpPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionGetMemento#getEmploymentCode()
	 */
	@Override
	public String getEmploymentCode() {
		return this.typeValue.getKshmtHdstkSetEmpPK().getEmpCtrCd();
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionGetMemento#getUpperLimitSetting()
	 */
	@Override
	public UpperLimitSetting getUpperLimitSetting() {
		return new UpperLimitSetting(new JpaUpperLimitEmpGetMemento(this.typeValue));
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * EmptYearlyRetentionGetMemento#getManagementCategory()
	 */
	@Override
	public ManageDistinct getManagementCategory() {
		return ManageDistinct.valueOf((int)this.typeValue.getManagementCtrAtr());
	}

}
