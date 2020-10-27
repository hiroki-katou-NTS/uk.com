/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiHolTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexODDeductionTimeGetMemento.
 */
public class JpaFlexODDeductionTimeGetMemento implements DeductionTimeGetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFiHolTs entity;
	

	/**
	 * Instantiates a new jpa flex OD deduction time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODDeductionTimeGetMemento(KshmtWtFleBrFiHolTs entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getStart()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getStrTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}

}
