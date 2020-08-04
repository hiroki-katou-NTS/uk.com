/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTimePK;

/**
 * The Class JpaWkpTransLaborTimeSetMemento.
 */
public class JpaWkpTransLaborTimeSetMemento implements WkpTransLaborTimeSetMemento {

	/** The entity. */
	private KshstWkpTransLabTime entity;

	/**
	 * Instantiates a new jpa wkp trans labor time set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWkpTransLaborTimeSetMemento(KshstWkpTransLabTime entity) {
		super();
		if (entity.getKshstWkpTransLabTimePK() == null) {
			entity.setKshstWkpTransLabTimePK(new KshstWkpTransLabTimePK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpTransLaborTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstWkpTransLabTimePK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.
	 * statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.
	 * common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.entity.getKshstWkpTransLabTimePK().setWkpId(workplaceId.v());
	}

}
