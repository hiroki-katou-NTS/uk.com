/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalWorkingTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime;

/**
 * The Class JpaWkpNormalWorkingTimeGetMemento.
 */
public class JpaWkpNormalWorkingTimeGetMemento implements WkpNormalWorkingTimeGetMemento {

	/** The entity. */
	private KshstWkpRegLaborTime entity;

	/**
	 * Instantiates a new jpa wkp normal working time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWkpNormalWorkingTimeGetMemento(KshstWkpRegLaborTime entity) {
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalWorkingTimeGetMemento#getWorkingTimeSet()
	 */
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTimeGetMemento#getWorkingTimeSet()
	 */
	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		WeeklyUnit weekyUnit = new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime()),
				WeekStart.valueOf(entity.getWeekStr()));
		DailyUnit dailyTime = new DailyUnit(new TimeOfDay(entity.getDailyTime()));
		return new WorkingTimeSetting(weekyUnit, dailyTime);
	}


	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalWorkingTimeGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstWkpRegLaborTimePK().getCid());
	}


	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalWorkingTimeGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.entity.getKshstWkpRegLaborTimePK().getWkpId());
	}

}
