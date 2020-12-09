/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFiAllTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDeductionTimeGetMemento.
 */
public class JpaDeductionTimeGetMemento implements DeductionTimeGetMemento {
	
	/** The entity. */
	private KshmtWtFloBrFiAllTs entity;
	
	/**
	 * Instantiates a new jpa deduction time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaDeductionTimeGetMemento(KshmtWtFloBrFiAllTs entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getStart()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getStrDay());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getEndDay());
	}

}
