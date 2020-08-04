/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpNormalSet;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingGetMemento;

/**
 * The Class JpaEmpNormalSettingGetMemento.
 */
public class JpaEmpNormalSettingGetMemento extends JpaDefaultSettingGetMemento implements EmpNormalSettingGetMemento {

	/** The entity. */
	private KshstEmpNormalSet entity;

	/**
	 * Instantiates a new jpa emp normal setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaEmpNormalSettingGetMemento(KshstEmpNormalSet entity) {
		super(entity);
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.NormalSettingGetMemento#getYear()
	 */
	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstEmpNormalSetPK().getYear());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstEmpNormalSetPK().getCid());
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.entity.getKshstEmpNormalSetPK().getEmpCd());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.NormalSettingGetMemento#getStatutorySetting()
	 */
	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		return this.toMonthlyUnits();
	}
	
	/**
	 * To monthly units.
	 *
	 * @return the list
	 */
	private List<MonthlyUnit> toMonthlyUnits() {
		return toMonthlyUnitsFromNormalSet();
	}

}
