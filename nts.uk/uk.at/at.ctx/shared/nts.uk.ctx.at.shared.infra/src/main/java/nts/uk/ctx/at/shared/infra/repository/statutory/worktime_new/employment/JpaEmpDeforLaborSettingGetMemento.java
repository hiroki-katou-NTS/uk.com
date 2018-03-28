/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpDeforLarSet;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingGetMemento;

/**
 * The Class JpaEmpDeforLaborSettingGetMemento.
 */
public class JpaEmpDeforLaborSettingGetMemento extends JpaDefaultSettingGetMemento implements EmpDeforLaborSettingGetMemento {
	
	/** The entity. */
	private KshstEmpDeforLarSet entity;

	/**
	 * Instantiates a new jpa emp defor labor setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaEmpDeforLaborSettingGetMemento(KshstEmpDeforLarSet entity) {
		super(entity);
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.entity.getKshstEmpDeforLarSetPK().getEmpCd());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getYear()
	 */
	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstEmpDeforLarSetPK().getYear());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstEmpDeforLarSetPK().getCid());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getStatutorySetting()
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
		return toMonthlyUnitsFromDeforSet();
	}
}
