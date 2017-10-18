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
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscmtChildCareSch;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscmtChildCareSchPK;

/**
 * The Class JpaChildCareScheduleSetMememto.
 */
public class JpaChildCareScheduleSetMememto implements ChildCareScheduleSetMemento {
	
	/** The entity. */
	private KscmtChildCareSch entity;
	
	/**
	 * Instantiates a new jpa child care schedule set mememto.
	 *
	 * @param entity the entity
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 */
	public JpaChildCareScheduleSetMememto(KscmtChildCareSch entity, String employeeId,
			GeneralDate baseDate) {
		if (entity.getKscmtChildCareSchPK() == null) {
			entity.setKscmtChildCareSchPK(new KscmtChildCareSchPK());
		}
		this.entity = entity;
		this.entity.getKscmtChildCareSchPK().setSid(employeeId);
		this.entity.getKscmtChildCareSchPK().setYmd(baseDate);
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
		this.entity.getKscmtChildCareSchPK().setChildCareNumber(childCareNumber.value);
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
		this.entity.setStrTime(childCareScheduleStart.getTimeOfDay().valueAsMinutes());
		this.entity.setStrDayAtr(childCareScheduleStart.getDayAtr().value);
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
		this.entity.setEndTime(childCareScheduleEnd.getTimeOfDay().valueAsMinutes());
		this.entity.setEndDayAtr(childCareScheduleEnd.getDayAtr().value);
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
