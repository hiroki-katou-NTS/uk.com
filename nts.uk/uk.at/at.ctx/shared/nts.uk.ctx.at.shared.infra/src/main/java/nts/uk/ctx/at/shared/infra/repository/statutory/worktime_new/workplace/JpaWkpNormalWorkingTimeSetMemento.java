/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalWorkingTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime;

/**
 * The Class JpaWkpRegularLaborTimeSetMemento.
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
public class JpaWkpNormalWorkingTimeSetMemento implements WkpNormalWorkingTimeSetMemento {

	/** The entity. */
	private KshstWkpRegLaborTime entity;

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstWkpRegLaborTimePK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.entity.getKshstWkpRegLaborTimePK().setWkpId(workplaceId.v());
	}

	
}
