/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTime;

/**
 * The Class JpaShainRegularLaborTimeGetMemento.
 */
public class JpaShainRegularLaborTimeGetMemento implements ShainRegularLaborTimeGetMemento {

	/** The entity. */
	private KshstShaRegLaborTime entity;

	/**
	 * Instantiates a new jpa shain regular labor time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaShainRegularLaborTimeGetMemento(KshstShaRegLaborTime entity) {
		this.entity = entity;
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstShaRegLaborTimePK().getCid());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeGetMemento#getWorkingTimeSet()
	 */
	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		WeeklyUnit weekyUnit = new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime()),
				WeekStart.valueOf(entity.getWeekStr()));
		DailyUnit dailyTime = new DailyUnit(new TimeOfDay(entity.getDailyTime()));
		return new WorkingTimeSetting(weekyUnit, dailyTime);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.entity.getKshstShaRegLaborTimePK().getSid());
	}

}
