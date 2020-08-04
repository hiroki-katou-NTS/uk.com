/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpFlexSetPK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingSetMemento;

/**
 * The Class JpaEmpFlexSettingSetMemento.
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */
@Getter
public class JpaEmpFlexSettingSetMemento extends JpaDefaultSettingSetMemento implements EmpFlexSettingSetMemento {
	
	/** The entity. */
	private KshstEmpFlexSet entity;

	/**
	 * Instantiates a new jpa emp flex setting set memento.
	 */
	public JpaEmpFlexSettingSetMemento(KshstEmpFlexSet entity) {
		if(entity.getKshstEmpFlexSetPK() == null) {
			entity.setKshstEmpFlexSetPK(new KshstEmpFlexSetPK());
		}
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstEmpFlexSetPK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		this.entity.getKshstEmpFlexSetPK().setYear(year.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingSetMemento#setEmploymentCode(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.entity.getKshstEmpFlexSetPK().setEmpCd(employmentCode.v());
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
}
