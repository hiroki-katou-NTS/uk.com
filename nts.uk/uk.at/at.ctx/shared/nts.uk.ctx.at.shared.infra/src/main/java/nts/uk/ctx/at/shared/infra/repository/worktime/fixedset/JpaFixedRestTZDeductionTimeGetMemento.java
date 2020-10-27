/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSet;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaFixedRestTZDeductionTimeGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaFixedRestTZDeductionTimeGetMemento<T extends ContractUkJpaEntity> implements DeductionTimeGetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa fixed rest TZ deduction time get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	JpaFixedRestTZDeductionTimeGetMemento(T entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getStart
	 * ()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		if (this.entity instanceof KshmtFixedHalfRestSet) {
			return new TimeWithDayAttr(((KshmtFixedHalfRestSet) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtFixedHolRestSet) {
			return new TimeWithDayAttr(((KshmtFixedHolRestSet) this.entity).getStartTime());
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		if (this.entity instanceof KshmtFixedHalfRestSet) {
			return new TimeWithDayAttr(((KshmtFixedHalfRestSet) this.entity).getEndTime());
		}
		if (this.entity instanceof KshmtFixedHolRestSet) {
			return new TimeWithDayAttr(((KshmtFixedHolRestSet) this.entity).getEndTime());
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
