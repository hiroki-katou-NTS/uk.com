/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.KuwstUsageUnitWtSet;

/**
 * The Class JpaUsageUnitSettingGetMemento.
 */
public class JpaUsageUnitSettingGetMemento implements UsageUnitSettingGetMemento {

	/** The Constant DEFINED_TRUE. */
	public static final int DEFINED_TRUE = 1;

	/** The setting. */
	private KuwstUsageUnitWtSet setting;

	/**
	 * Instantiates a new jpa usage unit setting get memento.
	 *
	 * @param setting the setting
	 */
	public JpaUsageUnitSettingGetMemento(KuwstUsageUnitWtSet setting) {
		this.setting = setting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.setting.getCid());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingGetMemento#getEmployee()
	 */
	@Override
	public boolean getEmployee() {
		return (this.setting.getIsEmp() == DEFINED_TRUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingGetMemento#getWorkPlace()
	 */
	@Override
	public boolean getWorkPlace() {
		return (this.setting.getIsWkp() == DEFINED_TRUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingGetMemento#getEmployment()
	 */
	@Override
	public boolean getEmployment() {
		return (this.setting.getIsEmpt() == DEFINED_TRUE);
	}


}
