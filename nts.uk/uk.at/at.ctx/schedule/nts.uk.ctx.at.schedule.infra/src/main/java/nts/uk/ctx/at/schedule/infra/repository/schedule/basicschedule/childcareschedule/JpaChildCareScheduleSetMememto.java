/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.childcareschedule;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ClockValue;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleRound;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscmtChildCareSchedule;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscmtChildCareSchedulePK;

/**
 * The Class JpaChildCareScheduleSetMememto.
 */
public class JpaChildCareScheduleSetMememto implements ChildCareScheduleSetMemento {
	
	/** The entity. */
	private KscmtChildCareSchedule entity;
	
	/**
	 * Instantiates a new jpa child care schedule set mememto.
	 *
	 * @param entity the entity
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 */
	public JpaChildCareScheduleSetMememto(KscmtChildCareSchedule entity, String employeeId,
			GeneralDate baseDate) {
		if (entity.getKscmtChildCareSchedulePK() == null) {
			entity.setKscmtChildCareSchedulePK(new KscmtChildCareSchedulePK());
		}
		this.entity = entity;
		this.entity.getKscmtChildCareSchedulePK().setSid(employeeId);
		this.entity.getKscmtChildCareSchedulePK().setYmd(baseDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleSetMemento#setChildCareNumber(nts.uk.ctx.at.schedule.dom
	 * .schedule.basicschedule.childcareschedule.ChildCareScheduleRound)
	 */
	@Override
	public void setChildCareNumber(ChildCareScheduleRound childCareNumber) {
		this.entity.getKscmtChildCareSchedulePK().setChildCareNumber(childCareNumber.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleSetMemento#setChildCareScheduleStart(nts.uk.ctx.at.
	 * schedule.dom.schedule.basicschedule.ClockValue)
	 */
	@Override
	public void setChildCareScheduleStart(ClockValue childCareScheduleStart) {
		this.entity.setSchcareTimeStart(childCareScheduleStart.getTimeOfDay().valueAsMinutes());
		this.entity.setSchcareDayatrStart(childCareScheduleStart.getDayAtr().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleSetMemento#setChildCareScheduleEnd(nts.uk.ctx.at.
	 * schedule.dom.schedule.basicschedule.ClockValue)
	 */
	@Override
	public void setChildCareScheduleEnd(ClockValue childCareScheduleEnd) {
		this.entity.setSchcareTimeEnd(childCareScheduleEnd.getTimeOfDay().valueAsMinutes());
		this.entity.setSchcareDayatrEnd(childCareScheduleEnd.getDayAtr().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleSetMemento#setChildCareAtr(nts.uk.ctx.at.schedule.dom.
	 * schedule.basicschedule.childcareschedule.ChildCareAtr)
	 */
	@Override
	public void setChildCareAtr(ChildCareAtr childCareAtr) {
		this.entity.setChildCareAtr(childCareAtr.value);

	}

}
