/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.KuwstUsageUnitWtSet;

/**
 * The Class JpaUsageUnitSettingSetMemento.
 */
public class JpaUsageUnitSettingSetMemento implements UsageUnitSettingSetMemento {

	/** The Constant DEFINED_TRUE. */
	public static final int DEFINED_TRUE = 1;

	/** The Constant DEFINED_FALSE. */
	public static final int DEFINED_FALSE = 0;

	/** The setting. */
	private KuwstUsageUnitWtSet setting;

	/**
	 * Instantiates a new jpa usage unit setting set memento.
	 *
	 * @param setting the setting
	 */
	public JpaUsageUnitSettingSetMemento(KuwstUsageUnitWtSet setting) {
		this.setting = setting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.setting.setCid(companyId.v());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingSetMemento#setEmployee(boolean)
	 */
	@Override
	public void setEmployee(boolean employee) {
		if (employee) {
			this.setting.setIsEmp(DEFINED_TRUE);
		} else {
			this.setting.setIsEmp(DEFINED_FALSE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingSetMemento#setWorkPlace(boolean)
	 */
	@Override
	public void setWorkPlace(boolean workPlace) {
		if (workPlace) {
			this.setting.setIsWkp(DEFINED_TRUE);
		} else {
			this.setting.setIsWkp(DEFINED_FALSE);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingSetMemento#setEmployment(boolean)
	 */
	@Override
	public void setEmployment(boolean employment) {
		if (employment) {
			this.setting.setIsEmpt(DEFINED_TRUE);
		} else {
			this.setting.setIsEmpt(DEFINED_FALSE);
		}

	}

}
