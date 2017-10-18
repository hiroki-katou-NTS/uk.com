/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.childcareschedule;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ClockValue;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleRound;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscmtChildCareSch;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscmtChildCareSchPK;
import nts.uk.shr.com.enumcommon.DayAttr;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * The Class JpaChildCareScheduleGetMemento.
 */
public class JpaChildCareScheduleGetMemento implements ChildCareScheduleGetMemento{

	/** The entity. */
	private KscmtChildCareSch entity;
	
	/**
	 * Instantiates a new jpa child care schedule get memento.
	 *
	 * @param entity the entity
	 */
	public JpaChildCareScheduleGetMemento(KscmtChildCareSch entity) {
		if (entity.getKscmtChildCareSchPK() == null) {
			entity.setKscmtChildCareSchPK(new KscmtChildCareSchPK());
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
		return ChildCareScheduleRound.valueOf(this.entity.getKscmtChildCareSchPK().getChildCareNumber());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareScheduleStart()
	 */
	@Override
	public ClockValue getChildCareScheduleStart() {
		return new ClockValue(new AttendanceClock(this.entity.getStrTime()),
				EnumAdaptor.valueOf(this.entity.getStrDayAtr(), DayAttr.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareScheduleEnd()
	 */
	@Override
	public ClockValue getChildCareScheduleEnd() {
		return new ClockValue(new AttendanceClock(this.entity.getEndTime()),
				EnumAdaptor.valueOf(this.entity.getEndDayAtr(), DayAttr.class));
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
