/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleWorkTsPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexEmTZSetTZRoundingSetMemento.
 */
public class JpaFlexEmTZSetTZRoundingSetMemento implements TimeZoneRoundingSetMemento{
	
	/** The entity. */
	private KshmtWtFleWorkTs entity;
	
	/**
	 * Instantiates a new jpa flex em TZ set TZ rounding set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexEmTZSetTZRoundingSetMemento(KshmtWtFleWorkTs entity) {
		super();
		if(entity.getKshmtWtFleWorkTsPK() == null){
			entity.setKshmtWtFleWorkTsPK(new KshmtWtFleWorkTsPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingSetMemento#
	 * setRounding(nts.uk.ctx.at.shared.dom.common.timerounding.
	 * TimeRoundingSetting)
	 */
	@Override
	public void setRounding(TimeRoundingSetting rdSet) {
		this.entity.setUnit(rdSet.getRoundingTime().value);
		this.entity.setRounding(rdSet.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingSetMemento#
	 * setStart(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStart(TimeWithDayAttr start) {
		this.entity.setTimeStr(start.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingSetMemento#
	 * setEnd(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.entity.setTimeEnd(end.valueAsMinutes());
	}
	

}
