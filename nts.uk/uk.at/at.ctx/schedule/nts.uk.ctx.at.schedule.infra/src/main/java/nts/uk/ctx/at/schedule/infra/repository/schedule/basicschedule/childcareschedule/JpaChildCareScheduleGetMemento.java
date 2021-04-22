/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.childcareschedule;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleRound;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscdtScheChildCare;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscdtScheChildCarePK;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaChildCareScheduleGetMemento.
 */
public class JpaChildCareScheduleGetMemento implements ChildCareScheduleGetMemento{

	/** The entity. */
	private KscdtScheChildCare entity;
	
	/**
	 * Instantiates a new jpa child care schedule get memento.
	 *
	 * @param entity the entity
	 */
	public JpaChildCareScheduleGetMemento(KscdtScheChildCare entity) {
		if (entity.getKscdtScheChildCarePK() == null) {
			entity.setKscdtScheChildCarePK(new KscdtScheChildCarePK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareNumber()
	 */
	@Override
	public ChildCareScheduleRound getChildCareNumber() {
		return ChildCareScheduleRound.valueOf(this.entity.getKscdtScheChildCarePK().getChildCareNumber());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareScheduleStart()
	 */
	@Override
	public TimeWithDayAttr getChildCareScheduleStart() {
		return new TimeWithDayAttr(this.entity.getStrTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareScheduleEnd()
	 */
	@Override
	public TimeWithDayAttr getChildCareScheduleEnd() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareAtr()
	 */
	@Override
	public ChildCareAtr getChildCareAtr() {
		return ChildCareAtr.valueOf(this.entity.getChildCareAtr());
	}

}
