/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwwstWeeklyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwwstWeeklyWorkSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWeeklyWorkSettingGetMemento.
 */
public class JpaWeeklyWorkSettingGetMemento implements WeeklyWorkSettingGetMemento{

	
	/** The entity. */
	private KwwstWeeklyWorkSet entity;
	
	/**
	 * Instantiates a new jpa weekly work setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWeeklyWorkSettingGetMemento(KwwstWeeklyWorkSet entity) {
		if(entity.getKwwstWeeklyWorkSetPK() == null){
			entity.setKwwstWeeklyWorkSetPK(new KwwstWeeklyWorkSetPK());
		}
		this.entity = entity;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingGetMemento
	 * #getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKwwstWeeklyWorkSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingGetMemento
	 * #getDayOfWeek()
	 */
	@Override
	public DayOfWeek getDayOfWeek() {
		return EnumAdaptor.valueOf(this.entity.getKwwstWeeklyWorkSetPK().getDayOfWeek(),
				DayOfWeek.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingGetMemento
	 * #getWorkdayDivision()
	 */
	@Override
	public WorkdayDivision getWorkdayDivision() {
		return EnumAdaptor.valueOf(this.entity.getWorkDayDiv(), WorkdayDivision.class);
	}

}
