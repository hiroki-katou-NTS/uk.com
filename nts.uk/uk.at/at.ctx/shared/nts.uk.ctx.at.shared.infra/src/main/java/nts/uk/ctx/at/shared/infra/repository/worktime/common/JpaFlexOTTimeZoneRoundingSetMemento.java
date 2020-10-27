/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleOverTsPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexOTTimeZoneRoundingSetMemento.
 */
public class JpaFlexOTTimeZoneRoundingSetMemento implements TimeZoneRoundingSetMemento{
	
	/** The entity. */
	private KshmtWtFleOverTs entity;
	
	/**
	 * Instantiates a new jpa flex OT time zone rounding set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexOTTimeZoneRoundingSetMemento(KshmtWtFleOverTs entity) {
		super();
		if(entity.getKshmtWtFleOverTsPK() == null){
			entity.setKshmtWtFleOverTsPK(new KshmtWtFleOverTsPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRoundingSetMemento#
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
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRoundingSetMemento#
	 * setStart(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStart(TimeWithDayAttr start) {
		this.entity.setTimeStr(start.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRoundingSetMemento#
	 * setEnd(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.entity.setTimeEnd(end.valueAsMinutes());
	}


}
