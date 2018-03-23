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
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTime;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingGetMemento;

/**
 * The Class JpaWkpTransLaborTimeGetMemento.
 */
public class JpaWkpTransLaborTimeGetMemento extends JpaDefaultSettingGetMemento implements WkpDeforLaborWorkTimeGetMemento {
	
	/** The entity. */
	private KshstWkpTransLabTime entity;

	/**
	 * Instantiates a new jpa wkp trans labor time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWkpTransLaborTimeGetMemento(KshstWkpTransLabTime entity) {
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstWkpTransLabTimePK().getCid());
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeGetMemento#getWorkingTimeSet()
	 */
	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		WeeklyUnit weekyUnit = new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime()), WeekStart.valueOf(entity.getWeekStr()));
		DailyUnit dailyTime = new DailyUnit(new TimeOfDay(entity.getDailyTime()));
		return new WorkingTimeSetting(weekyUnit, dailyTime);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.entity.getKshstWkpTransLabTimePK().getWkpId());
	}
}
