/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.performance;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHalfRestTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHolRestTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowFixedRtSet;
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
		if (this.entity instanceof KshmtFixedHalfRestSet) {
			return new TimeWithDayAttr(((KshmtFixedHalfRestSet) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtFixedHolRestSet) {
			return new TimeWithDayAttr(((KshmtFixedHolRestSet) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtDtHolRestTime) {
			return new TimeWithDayAttr(((KshmtDtHolRestTime) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtDtHalfRestTime) {
			return new TimeWithDayAttr(((KshmtDtHalfRestTime) this.entity).getStartTime());
		}
		if (this.entity instanceof KshmtFlowFixedRtSet) {
			return new TimeWithDayAttr(((KshmtFlowFixedRtSet) this.entity).getStrDay());
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
		if (this.entity instanceof KshmtDtHolRestTime) {
			return new TimeWithDayAttr(((KshmtDtHolRestTime) this.entity).getEndTime());
		}
		if (this.entity instanceof KshmtDtHalfRestTime) {
			return new TimeWithDayAttr(((KshmtDtHalfRestTime) this.entity).getEndTime());
		}
		if (this.entity instanceof KshmtFlowFixedRtSet) {
			return new TimeWithDayAttr(((KshmtFlowFixedRtSet) this.entity).getEndDay());
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
		if (this.entity instanceof KshmtFixedHalfRestSet) {
			return AmPmAtr.valueOf(((KshmtFixedHalfRestSet) this.entity)
					.getKshmtFixedHalfRestSetPK().getAmPmAtr());
		}
		if (this.entity instanceof KshmtFixedHolRestSet) {
			return null;
		}
		if (this.entity instanceof KshmtDtHolRestTime) {
			return null;
		}
		if (this.entity instanceof KshmtDtHalfRestTime) {
			return AmPmAtr.valueOf(((KshmtDtHalfRestTime) this.entity)
					.getKshmtDtHalfRestTimePK().getAmPmAtr());
		}
		if (this.entity instanceof KshmtFlowFixedRtSet) {
			return null;
		}

		throw new IllegalStateException("entity type is not valid");
	}

}
