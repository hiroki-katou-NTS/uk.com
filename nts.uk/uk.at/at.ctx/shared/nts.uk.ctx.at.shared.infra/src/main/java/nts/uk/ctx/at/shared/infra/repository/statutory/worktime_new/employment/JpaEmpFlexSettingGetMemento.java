/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpFlexSet;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingGetMemento;

/**
 * The Class JpaEmpFlexSettingGetMemento.
 */
public class JpaEmpFlexSettingGetMemento extends JpaDefaultSettingGetMemento implements EmpFlexSettingGetMemento {
	
	/** The entity. */
	private KshstEmpFlexSet entity;

	/**
	 * Instantiates a new jpa emp flex setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaEmpFlexSettingGetMemento(KshstEmpFlexSet entity) {
		super(entity);
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getYear()
	 */
	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstEmpFlexSetPK().getYear());
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.entity.getKshstEmpFlexSetPK().getEmpCd());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstEmpFlexSetPK().getCid());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getStatutorySetting()
	 */
	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		return toStatutorySettingFromFlexSet();
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getSpecifiedSetting()
	 */
	@Override
	public List<MonthlyUnit> getSpecifiedSetting() {
		return toSpecSettingFromFlexSet();
	}
	
	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingGetMemento#getWeekAveSetting()
	 */
	@Override
	public List<MonthlyUnit> getWeekAveSetting() {
		return toWeekSettingFromFlexSet();
	}
}
