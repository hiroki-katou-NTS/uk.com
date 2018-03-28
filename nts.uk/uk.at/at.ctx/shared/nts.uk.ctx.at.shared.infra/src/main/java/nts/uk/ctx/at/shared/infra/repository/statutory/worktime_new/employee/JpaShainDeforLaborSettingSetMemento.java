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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSetPK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingSetMemento;

/**
 * The Class JpaShainDeforLaborSettingSetMemento.
 */

@Getter
public class JpaShainDeforLaborSettingSetMemento extends JpaDefaultSettingSetMemento
		implements ShainDeforLaborSettingSetMemento {

	/** The entity. */
	private KshstShaDeforLarSet entity;

	/**
	 * Instantiates a new jpa shain defor labor setting set memento.
	 */
	public JpaShainDeforLaborSettingSetMemento(KshstShaDeforLarSet entity) {
		if(entity.getKshstShaDeforLarSetPK() == null) {
			entity.setKshstShaDeforLarSetPK(new KshstShaDeforLarSetPK());
		}
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		this.entity.getKshstShaDeforLarSetPK().setYear(year.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingSetMemento#setStatutorySetting(java.util.List)
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		setStatutorySettingToDeforSet(this.entity, statutorySetting);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstShaDeforLarSetPK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingSetMemento#setEmployeeId(nts.uk.ctx.at.shared.dom.common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.entity.getKshstShaDeforLarSetPK().setSid(employeeId.v());
	}
}
