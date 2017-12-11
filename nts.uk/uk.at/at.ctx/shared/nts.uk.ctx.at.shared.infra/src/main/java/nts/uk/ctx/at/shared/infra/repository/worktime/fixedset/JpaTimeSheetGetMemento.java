/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSetPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaTimeSheetGetMemento.
 */
public class JpaTimeSheetGetMemento implements TimeSheetGetMemento {
	
	/** The entity. */
	private KshmtFlexWorkSet entity;
	
	/**
	 * Instantiates a new jpa time sheet get memento.
	 *
	 * @param entity the entity
	 */
	public JpaTimeSheetGetMemento(KshmtFlexWorkSet entity) {
		super();
		if(entity.getKshmtFlexWorkSetPK() == null){
			entity.setKshmtFlexWorkSetPK(new KshmtFlexWorkSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetGetMemento#
	 * getStartTime()
	 */
	@Override
	public TimeWithDayAttr getStartTime() {
		return new TimeWithDayAttr(this.entity.getCoreTimeStr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetGetMemento#getEndTime(
	 * )
	 */
	@Override
	public TimeWithDayAttr getEndTime() {
		return new TimeWithDayAttr(this.entity.getCoreTimeEnd());
	}

}
