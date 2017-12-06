/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFlexHolSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFlexHolSetPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaHDWTSheetTimeZoneRoundingGetMemento.
 */
public class JpaHDWTSheetTimeZoneRoundingGetMemento implements TimeZoneRoundingGetMemento{

	/** The entity. */
	private KshmtFlexHolSet entity;
	
	/**
	 * Instantiates a new jpa HDWT sheet time zone rounding get memento.
	 *
	 * @param entity the entity
	 */
	public JpaHDWTSheetTimeZoneRoundingGetMemento(KshmtFlexHolSet entity) {
		super();
		if(entity.getKshmtFlexHolSetPK() == null){
			entity.setKshmtFlexHolSetPK(new KshmtFlexHolSetPK());
		}
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRoundingGetMemento#getRounding()
	 */
	@Override
	public TimeRoundingSetting getRounding() {
		return new TimeRoundingSetting(this.entity.getUnit(), this.entity.getRounding());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRoundingGetMemento#getStart()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getTimeStr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRoundingGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getTimeEnd());
	}

}
