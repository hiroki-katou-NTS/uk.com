/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.childcareschedule;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleRound;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscdtScheChildCare;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscdtScheChildCarePK;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaChildCareScheduleSetMememto.
 */
public class JpaChildCareScheduleSetMememto implements ChildCareScheduleSetMemento {
	
	/** The entity. */
	private KscdtScheChildCare entity;
	
	/**
	 * Instantiates a new jpa child care schedule set mememto.
	 *
	 * @param entity the entity
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 */
	public JpaChildCareScheduleSetMememto(KscdtScheChildCare entity, String employeeId,
			GeneralDate baseDate) {
		if (entity.getKscdtScheChildCarePK() == null) {
			entity.setKscdtScheChildCarePK(new KscdtScheChildCarePK());
		}
		this.entity = entity;
		this.entity.getKscdtScheChildCarePK().setSid(employeeId);
		this.entity.getKscdtScheChildCarePK().setYmd(baseDate);
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
		this.entity.getKscdtScheChildCarePK().setChildCareNumber(childCareNumber.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleSetMemento#setChildCareScheduleStart(nts.uk.ctx.at.
	 * schedule.dom.schedule.basicschedule.ClockValue)
	 */
	@Override
	public void setChildCareScheduleStart(TimeWithDayAttr childCareScheduleStart) {
		this.entity.setStrTime(childCareScheduleStart.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleSetMemento#setChildCareScheduleEnd(nts.uk.ctx.at.
	 * schedule.dom.schedule.basicschedule.ClockValue)
	 */
	@Override
	public void setChildCareScheduleEnd(TimeWithDayAttr childCareScheduleEnd) {
		this.entity.setEndTime(childCareScheduleEnd.valueAsMinutes());
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
