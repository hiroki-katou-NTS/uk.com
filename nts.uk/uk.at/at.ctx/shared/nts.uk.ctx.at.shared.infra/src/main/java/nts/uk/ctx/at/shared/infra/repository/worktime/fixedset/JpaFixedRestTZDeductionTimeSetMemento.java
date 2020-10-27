/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSet;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaFixedRestTZDeductionTimeSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaFixedRestTZDeductionTimeSetMemento<T extends ContractUkJpaEntity> implements DeductionTimeSetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa fixed rest TZ deduction time set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedRestTZDeductionTimeSetMemento(T entity) {
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
		if (this.entity instanceof KshmtFixedHalfRestSet) {
			((KshmtFixedHalfRestSet) this.entity).setStartTime(start.valueAsMinutes());
			return;
		}
		if (this.entity instanceof KshmtFixedHolRestSet) {
			((KshmtFixedHolRestSet) this.entity).setStartTime(start.valueAsMinutes());
			return;
		}
		throw new IllegalStateException("entity type is not valid");
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
		if (this.entity instanceof KshmtFixedHalfRestSet) {
			((KshmtFixedHalfRestSet) this.entity).setEndTime(end.valueAsMinutes());
			return;
		}
		if (this.entity instanceof KshmtFixedHolRestSet) {
			((KshmtFixedHolRestSet) this.entity).setEndTime(end.valueAsMinutes());
			return;
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
