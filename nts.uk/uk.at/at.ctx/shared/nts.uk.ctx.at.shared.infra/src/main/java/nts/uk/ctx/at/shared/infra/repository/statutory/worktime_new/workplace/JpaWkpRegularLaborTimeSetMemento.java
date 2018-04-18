/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTimePK;

/**
 * The Class JpaWkpRegularLaborTimeSetMemento.
 */
public class JpaWkpRegularLaborTimeSetMemento implements WkpRegularLaborTimeSetMemento {

	/** The entity. */
	private KshstWkpRegLaborTime entity;

	/**
	 * Instantiates a new jpa wkp regular labor time set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWkpRegularLaborTimeSetMemento(KshstWkpRegLaborTime entity) {
		super();		
		if(entity.getKshstWkpRegLaborTimePK() == null){				
		}
		entity.setKshstWkpRegLaborTimePK(new KshstWkpRegLaborTimePK());
		this.entity = entity;
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpRegularLaborTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstWkpRegLaborTimePK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.
	 * common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.entity.getKshstWkpRegLaborTimePK().setWkpId(workplaceId.v());
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpRegularLaborTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.
	 * statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

}
