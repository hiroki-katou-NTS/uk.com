/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFle;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFlePK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexTimeSheetGetMemento.
 */
public class JpaFlexTimeSheetGetMemento implements TimeSheetGetMemento {
	
	/** The entity. */
	private KshmtWtFle entity;
	
	/**
	 * Instantiates a new jpa time sheet get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexTimeSheetGetMemento(KshmtWtFle entity) {
		super();
		if(entity.getKshmtWtFlePK() == null){
			entity.setKshmtWtFlePK(new KshmtWtFlePK());
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
