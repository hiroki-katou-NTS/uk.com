/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime;

/**
 * The Class JpaComTransLaborTimeSetMemento.
 */
public class JpaComTransLaborTimeSetMemento implements ComTransLaborTimeSetMemento {

	/** The entity. */
	private KshstComTransLabTime entity;

	/**
	 * Instantiates a new jpa com trans labor time set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaComTransLaborTimeSetMemento(KshstComTransLabTime entity) {
		super();
		this.entity = entity;
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComTransLaborTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComTransLaborTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.
	 * statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		// Set work time setting.
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

}
