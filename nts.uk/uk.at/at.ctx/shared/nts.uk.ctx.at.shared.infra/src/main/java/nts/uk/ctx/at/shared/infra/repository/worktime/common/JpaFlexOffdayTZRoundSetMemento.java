/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleHolTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexOffdayTZRoundSetMemento.
 */
public class JpaFlexOffdayTZRoundSetMemento implements TimeZoneRoundingSetMemento{
	
	/** The entity. */
	private KshmtWtFleHolTs entity;

	/**
	 * Instantiates a new jpa flex offday TZ round set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexOffdayTZRoundSetMemento(KshmtWtFleHolTs entity) {
		super();
		this.entity = entity;
	}


	/**
	 * Sets the rounding.
	 *
	 * @param rdSet the new rounding
	 */
	@Override
	public void setRounding(TimeRoundingSetting rdSet) {
		this.entity.setUnit(rdSet.getRoundingTime().value);
		this.entity.setRounding(rdSet.getRounding().value);
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	@Override
	public void setStart(TimeWithDayAttr start) {
		this.entity.setTimeStr(start.valueAsMinutes());
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.entity.setTimeEnd(end.valueAsMinutes());
	}
	

}
