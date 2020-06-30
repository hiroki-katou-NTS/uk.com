/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSet;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingGetMemento;

/**
 * The Class JpaEmpFlexSettingGetMemento.
 */
public class JpaShainFlexSettingGetMemento extends JpaDefaultSettingGetMemento implements ShainFlexSettingGetMemento {
	
	private KshstShaFlexSet entity;

	public JpaShainFlexSettingGetMemento(KshstShaFlexSet entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstShaFlexSetPK().getYear());
	}

	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		return toStatutorySettingFromFlexSet();
	}

	@Override
	public List<MonthlyUnit> getSpecifiedSetting() {
		return toSpecSettingFromFlexSet();
	}

	@Override
	public List<MonthlyUnit> getWeekAveSetting() {
		return toWeekSettingFromFlexSet();
	}
	
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstShaFlexSetPK().getCid());
	}

	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.entity.getKshstShaFlexSetPK().getSid());
	}
}
