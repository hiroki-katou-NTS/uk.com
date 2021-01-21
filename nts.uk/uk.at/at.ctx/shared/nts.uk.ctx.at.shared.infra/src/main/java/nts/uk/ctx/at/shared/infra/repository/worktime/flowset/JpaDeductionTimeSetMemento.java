/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFiAllTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDeductionTimeSetMemento.
 */
public class JpaDeductionTimeSetMemento implements DeductionTimeSetMemento {

	/** The entity. */
	private KshmtWtFloBrFiAllTs entity;
	
	/**
	 * Instantiates a new jpa deduction time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaDeductionTimeSetMemento(KshmtWtFloBrFiAllTs entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento#setStart(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStart(TimeWithDayAttr start) {
		this.entity.setStrDay(start.valueAsMinutes());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento#setEnd(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.entity.setEndDay(end.valueAsMinutes());
	}

}
