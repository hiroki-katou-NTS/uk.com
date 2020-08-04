/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSet;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingGetMemento;

/**
 * The Class JpaShainDeforLaborSettingGetMemento.
 */
public class JpaShainDeforLaborSettingGetMemento extends JpaDefaultSettingGetMemento implements ShainDeforLaborSettingGetMemento {
	
	/** The entity. */
	private KshstShaDeforLarSet entity;
	
	/**
	 * Instantiates a new jpa shain defor labor setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaShainDeforLaborSettingGetMemento(KshstShaDeforLarSet entity) {
		super(entity);
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getYear()
	 */
	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstShaDeforLarSetPK().getYear());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingGetMemento#getStatutorySetting()
	 */
	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		return toMonthlyUnitsFromDeforSet();
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstShaDeforLarSetPK().getCid());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.entity.getKshstShaDeforLarSetPK().getSid());
	}

}
