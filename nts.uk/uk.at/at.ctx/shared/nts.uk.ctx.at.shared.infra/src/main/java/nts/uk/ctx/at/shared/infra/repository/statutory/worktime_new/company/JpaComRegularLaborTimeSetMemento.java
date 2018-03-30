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
 * The Class JpaComRegularLaborTimeSetMemento.
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */
@Getter
public class JpaComRegularLaborTimeSetMemento implements ComRegularLaborTimeSetMemento {
	
	/** The entity. */
	private KshstComRegLaborTime entity;

	/**
	 * Instantiates a new jpa com regular labor time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaComRegularLaborTimeSetMemento(KshstComRegLaborTime entity) {
		super();
		this.entity = entity;
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

}
