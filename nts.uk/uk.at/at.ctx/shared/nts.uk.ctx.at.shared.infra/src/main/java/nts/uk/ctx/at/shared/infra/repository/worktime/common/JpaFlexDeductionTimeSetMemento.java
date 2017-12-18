/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexFixRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexFixRestSetPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexDeductionTimeSetMemento.
 */
public class JpaFlexDeductionTimeSetMemento implements DeductionTimeSetMemento{
	
	/** The entity. */
	private KshmtFlexFixRestSet entity;

	/**
	 * Instantiates a new jpa flex deduction time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexDeductionTimeSetMemento(KshmtFlexFixRestSet entity) {
		super();
		if(entity.getKshmtFlexFixRestSetPK() == null){
			entity.setKshmtFlexFixRestSetPK(new KshmtFlexFixRestSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento#setStart
	 * (nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStart(TimeWithDayAttr start) {
		this.entity.setStrTime(start.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento#setEnd(
	 * nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.entity.setEndTime(end.valueAsMinutes());
	}

	
	

}
