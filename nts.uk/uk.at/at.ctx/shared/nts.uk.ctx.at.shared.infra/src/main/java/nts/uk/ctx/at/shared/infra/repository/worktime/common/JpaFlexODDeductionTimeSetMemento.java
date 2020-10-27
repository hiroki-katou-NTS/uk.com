/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiHolTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexODDeductionTimeSetMemento.
 */
public class JpaFlexODDeductionTimeSetMemento implements DeductionTimeSetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFiHolTs entity;
	

	/**
	 * Instantiates a new jpa flex OD deduction time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODDeductionTimeSetMemento(KshmtWtFleBrFiHolTs entity) {
		super();
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
