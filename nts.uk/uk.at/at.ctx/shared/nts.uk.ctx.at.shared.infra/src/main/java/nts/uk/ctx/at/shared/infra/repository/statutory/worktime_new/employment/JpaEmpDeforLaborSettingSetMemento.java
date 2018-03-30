/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpDeforLarSetPK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingSetMemento;

/**
 * The Class JpaEmpDeforLaborSettingSetMemento.
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */
@Getter
public class JpaEmpDeforLaborSettingSetMemento extends JpaDefaultSettingSetMemento implements EmpDeforLaborSettingSetMemento {
	
	/** The entity. */
	private KshstEmpDeforLarSet entity;

	/**
	 * Instantiates a new jpa emp defor labor setting set memento.
	 */
	public JpaEmpDeforLaborSettingSetMemento(KshstEmpDeforLarSet entity) {
		if(entity.getKshstEmpDeforLarSetPK() == null) {
			entity.setKshstEmpDeforLarSetPK(new KshstEmpDeforLarSetPK());
		}
		this.entity = entity; 
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingSetMemento#setEmploymentCode(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.entity.getKshstEmpDeforLarSetPK().setEmpCd(employmentCode.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		this.entity.getKshstEmpDeforLarSetPK().setYear(year.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstEmpDeforLarSetPK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingSetMemento#setStatutorySetting(java.util.List)
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		setStatutorySettingToDeforSet(this.entity, statutorySetting);
	}
}
