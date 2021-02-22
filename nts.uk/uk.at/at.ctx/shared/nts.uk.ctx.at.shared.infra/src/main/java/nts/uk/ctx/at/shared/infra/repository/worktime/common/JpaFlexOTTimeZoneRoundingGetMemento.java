/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleOverTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexOTTimeZoneRoundingGetMemento.
 */
public class JpaFlexOTTimeZoneRoundingGetMemento implements TimeZoneRoundingGetMemento{
	
	/** The entity. */
	private KshmtWtFleOverTs entity;
	
	/**
	 * Instantiates a new jpa flex OT time zone rounding get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexOTTimeZoneRoundingGetMemento(KshmtWtFleOverTs entity) {
		super();
		this.entity = entity;
	}

	/**
	 * Gets the rounding.
	 *
	 * @return the rounding
	 */
	@Override
	public TimeRoundingSetting getRounding() {
		return new TimeRoundingSetting(this.entity.getUnit(), this.entity.getRounding());
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getTimeStr());
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getTimeEnd());
	}
	

}
