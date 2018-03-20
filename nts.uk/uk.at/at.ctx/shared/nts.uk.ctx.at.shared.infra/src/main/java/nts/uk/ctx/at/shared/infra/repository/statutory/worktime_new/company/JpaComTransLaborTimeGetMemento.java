/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime;

/**
 * The Class JpaCompanySettingGetMemento.
 */
public class JpaComTransLaborTimeGetMemento implements ComTransLaborTimeGetMemento {
	
	private CompanyId companyId;

	private WorkingTimeSetting workingTimeSet;

	public JpaComTransLaborTimeGetMemento(KshstComTransLabTime entity) {
		this.companyId = new CompanyId(entity.getCid());
		
		WeeklyUnit weekyUnit = new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime()), WeekStart.valueOf(entity.getWeekStr()));
		DailyUnit dailyTime = new DailyUnit(new TimeOfDay(entity.getDailyTime()));
		this.workingTimeSet = new WorkingTimeSetting(weekyUnit, dailyTime);
	}

	@Override
	public CompanyId getCompanyId() {
		return this.companyId;
	}

	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		return this.workingTimeSet;
	}

}
