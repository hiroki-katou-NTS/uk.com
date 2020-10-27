/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.performance;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFiAllTs;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaFixedRestTZDeductionTimeGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaAmPmWorkTimezoneGetMemento<T extends ContractUkJpaEntity>
		implements AmPmWorkTimezoneGetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa fixed rest TZ deduction time get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaAmPmWorkTimezoneGetMemento(T entity) {
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
		if (this.entity instanceof KshmtWtFixBrWekTs) {
			return new TimeWithDayAttr(((KshmtWtFixBrWekTs) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtWtFixBrHolTs) {
			return new TimeWithDayAttr(((KshmtWtFixBrHolTs) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtWtDifBrHolTs) {
			return new TimeWithDayAttr(((KshmtWtDifBrHolTs) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtWtDifBrWekTs) {
			return new TimeWithDayAttr(((KshmtWtDifBrWekTs) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtWtFloBrFiAllTs) {
			return new TimeWithDayAttr(((KshmtWtFloBrFiAllTs) this.entity).getStrDay());
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
		if (this.entity instanceof KshmtWtFixBrWekTs) {
			return new TimeWithDayAttr(((KshmtWtFixBrWekTs) this.entity).getEndTime());
		}
		if (this.entity instanceof KshmtWtFixBrHolTs) {
			return new TimeWithDayAttr(((KshmtWtFixBrHolTs) this.entity).getEndTime());
		}
		if (this.entity instanceof KshmtWtDifBrHolTs) {
			return new TimeWithDayAttr(((KshmtWtDifBrHolTs) this.entity).getEndTime());
		}
		if (this.entity instanceof KshmtWtDifBrWekTs) {
			return new TimeWithDayAttr(((KshmtWtDifBrWekTs) this.entity).getEndTime());
		}
		if (this.entity instanceof KshmtWtFloBrFiAllTs) {
			return new TimeWithDayAttr(((KshmtWtFloBrFiAllTs) this.entity).getEndDay());
		}

		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezoneGetMemento#
	 * getAmPmAtr()
	 */
	@Override
	public AmPmAtr getAmPmAtr() {
		if (this.entity instanceof KshmtWtFixBrWekTs) {
			return AmPmAtr.valueOf(((KshmtWtFixBrWekTs) this.entity)
					.getKshmtWtFixBrWekTsPK().getAmPmAtr());
		}
		if (this.entity instanceof KshmtWtFixBrHolTs) {
			return null;
		}
		if (this.entity instanceof KshmtWtDifBrHolTs) {
			return null;
		}
		if (this.entity instanceof KshmtWtDifBrWekTs) {
			return AmPmAtr.valueOf(((KshmtWtDifBrWekTs) this.entity)
					.getKshmtWtDifBrWekTsPK().getAmPmAtr());
		}
		if (this.entity instanceof KshmtWtFloBrFiAllTs) {
			return null;
		}

		throw new IllegalStateException("entity type is not valid");
	}

}
