/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComRegLaborTime;

/**
 * The Class JpaCompanyWtSettingSetMemento.
 */
@Getter
public class JpaComRegularLaborTimeSetMemento implements ComRegularLaborTimeSetMemento {
	
	private KshstComRegLaborTime entity;

	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

}
