/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSet;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingGetMemento;

/**
 * The Class JpaShaNormalSettingGetMemento.
 */
public class JpaShainNormalSettingGetMemento extends JpaDefaultSettingGetMemento implements ShainNormalSettingGetMemento {

	private KshstShaNormalSet entity;

	public JpaShainNormalSettingGetMemento(KshstShaNormalSet entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstShaNormalSetPK().getYear());
	}

	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		return toMonthlyUnitsFromNormalSet();
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstShaNormalSetPK().getCid());
	}

	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.entity.getKshstShaNormalSetPK().getSid());
	}


}
