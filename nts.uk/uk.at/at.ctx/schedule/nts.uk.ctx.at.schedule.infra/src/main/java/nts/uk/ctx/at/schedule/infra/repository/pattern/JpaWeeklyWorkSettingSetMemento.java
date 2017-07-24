/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwwstWeeklyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwwstWeeklyWorkSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWeeklyWorkSettingSetMemento.
 */
public class JpaWeeklyWorkSettingSetMemento implements WeeklyWorkSettingSetMemento{
	
	/** The entity. */
	private KwwstWeeklyWorkSet entity;
	
	/**
	 * Instantiates a new jpa weekly work setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWeeklyWorkSettingSetMemento(KwwstWeeklyWorkSet entity) {
		if(entity.getKwwstWeeklyWorkSetPK() == null){
			entity.setKwwstWeeklyWorkSetPK(new KwwstWeeklyWorkSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKwwstWeeklyWorkSetPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setDayOfWeek(nts.uk.ctx.at.schedule.dom.shift.pattern.work.DayOfWeek)
	 */
	@Override
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.entity.getKwwstWeeklyWorkSetPK().setDayOfWeek(dayOfWeek.value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setWorkdayDivision(nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkdayDivision)
	 */
	@Override
	public void setWorkdayDivision(WorkdayDivision workdayDivision) {
		this.entity.setWorkDayDiv(workdayDivision.value);
	}

}
