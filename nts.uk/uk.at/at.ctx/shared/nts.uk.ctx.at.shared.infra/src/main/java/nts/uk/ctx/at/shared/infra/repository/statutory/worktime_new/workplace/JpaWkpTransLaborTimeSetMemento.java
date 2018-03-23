/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTime;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingSetMemento;

/**
 * The Class JpaWkpTransLaborTimeSetMemento.
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

/**
 * Gets the entity.
 *
 * @return the entity
 */
@Getter
public class JpaWkpTransLaborTimeSetMemento extends JpaDefaultSettingSetMemento implements WkpDeforLaborWorkTimeSetMemento {

	/** The entity. */
	private KshstWkpTransLabTime entity;

	/**
	 * Instantiates a new jpa wkp trans labor time set memento.
	 */
	public JpaWkpTransLaborTimeSetMemento() {
		this.entity = new KshstWkpTransLabTime();
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTimeSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstWkpTransLabTimePK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTimeSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew) {
		this.entity.setDailyTime(workingTimeSettingNew.getDailyTime().getDailyTime().v());
		this.entity.setWeeklyTime(workingTimeSettingNew.getWeeklyTime().getTime().v());
		this.entity.setWeekStr(workingTimeSettingNew.getWeeklyTime().getStart().value);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.entity.getKshstWkpTransLabTimePK().setWkpId(workplaceId.v());
	}


}
