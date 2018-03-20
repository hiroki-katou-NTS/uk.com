/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComRegLaborTime;

/**
 * The Class JpaCompanySettingGetMemento.
 */
public class JpaComRegularLaborTimeGetMemento implements ComRegularLaborTimeGetMemento {
	
	private KshstComRegLaborTime entity;
	
	public JpaComRegularLaborTimeGetMemento(KshstComRegLaborTime entity) {
		this.entity = entity;
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getCid());
	}

	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		WeeklyUnit weekyUnit = new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime()), WeekStart.valueOf(entity.getWeekStr()));
		DailyUnit dailyTime = new DailyUnit(new TimeOfDay(entity.getDailyTime()));
		return new WorkingTimeSetting(weekyUnit, dailyTime);
	}

}
