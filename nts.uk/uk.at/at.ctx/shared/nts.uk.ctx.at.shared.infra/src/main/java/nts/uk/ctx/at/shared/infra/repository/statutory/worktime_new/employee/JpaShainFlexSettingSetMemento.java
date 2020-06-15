/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSetPK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingSetMemento;

/**
 * The Class JpaShainFlexSettingSetMemento.
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */
@Getter
public class JpaShainFlexSettingSetMemento extends JpaDefaultSettingSetMemento implements ShainFlexSettingSetMemento {
	
	/** The entity. */
	private KshstShaFlexSet entity;

	/**
	 * Instantiates a new jpa shain flex setting set memento.
	 */
	public JpaShainFlexSettingSetMemento(KshstShaFlexSet entity) {
		if(entity.getKshstShaFlexSetPK() == null) {
			entity.setKshstShaFlexSetPK(new KshstShaFlexSetPK());
		}
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		this.entity.getKshstShaFlexSetPK().setYear(year.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingSetMemento#setStatutorySetting(java.util.List)
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		setStatutorySettingToFlexSet(this.entity, statutorySetting);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingSetMemento#setSpecifiedSetting(java.util.List)
	 */
	@Override
	public void setSpecifiedSetting(List<MonthlyUnit> specifiedSetting) {
		setSpecSettingToFlexSet(this.entity, specifiedSetting);
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingSetMemento#setWeekAveSetting(java.util.List)
	 */
	@Override
	public void setWeekAveSetting(List<MonthlyUnit> weekAveSetting) {
		setWeekSettingToFlexSet(this.entity, weekAveSetting);
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstShaFlexSetPK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingSetMemento#setEmployeeId(nts.uk.ctx.at.shared.dom.common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.entity.getKshstShaFlexSetPK().setSid(employeeId.v());
	}
}
