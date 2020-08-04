/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTimePK;

/**
 * The Class JpaShainRegularLaborTimeSetMemento.
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
public class JpaShainRegularLaborTimeSetMemento implements ShainRegularLaborTimeSetMemento {

	/** The entity. */
	private KshstShaRegLaborTime entity;

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */

	public JpaShainRegularLaborTimeSetMemento(KshstShaRegLaborTime entity) {
		if(entity.getKshstShaRegLaborTimePK() == null) {
			entity.setKshstShaRegLaborTimePK(new KshstShaRegLaborTimePK());
		}
		this.entity = entity;
	}
	
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstShaRegLaborTimePK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeSetMemento#setEmployeeId(nts.uk.ctx.at.shared.dom.common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.entity.getKshstShaRegLaborTimePK().setSid(employeeId.v());
	}

}
