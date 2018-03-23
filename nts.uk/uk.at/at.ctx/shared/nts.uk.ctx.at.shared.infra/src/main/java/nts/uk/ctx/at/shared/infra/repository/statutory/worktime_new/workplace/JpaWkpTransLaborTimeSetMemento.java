/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.WkpTransLaborTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstWkpTransLabTime;

/**
 * The Class JpaWkpTransLaborTimeSetMemento.
 */
public class JpaWkpTransLaborTimeSetMemento implements WkpTransLaborTimeSetMemento {

	/** The entity. */
	private KshstWkpTransLabTime entity;

	/**
	 * Instantiates a new jpa com trans labor time set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWkpTransLaborTimeSetMemento(KshstWkpTransLabTime entity) {
		super();
		this.entity = entity;
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpTransLaborTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpTransLaborTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.
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
