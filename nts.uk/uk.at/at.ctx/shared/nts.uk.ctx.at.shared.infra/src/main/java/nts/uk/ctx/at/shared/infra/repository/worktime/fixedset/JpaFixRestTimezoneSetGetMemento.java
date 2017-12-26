/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class JpaFixRestTimezoneSetGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaFixRestTimezoneSetGetMemento<T extends UkJpaEntity> implements FixRestTimezoneSetGetMemento {

	/** The entity. */
	private List<T> entitySets;

	/**
	 * Instantiates a new jpa fix rest timezone set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixRestTimezoneSetGetMemento(List<T> entitySets) {
		super();
		this.entitySets = entitySets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetGetMemento#
	 * getLstTimezone()
	 */
	@Override
	public List<DeductionTime> getLstTimezone() {
		if (this.entitySets instanceof KshmtFixedHalfRestSet) {
			// KSHMT_FIXED_HALF_REST_SET
			return this.entitySets.stream()
					.map(KshmtFixedHalfRestSet.class::cast)
					.map(entity -> new DeductionTime(
							new JpaFixedRestTZDeductionTimeGetMemento(entity.getStartTime(), entity.getEndTime())))
					.collect(Collectors.toList());
		}
		if (this.entitySets instanceof KshmtFixedHolRestSet) {
			return this.entitySets.stream()
					.map(KshmtFixedHolRestSet.class::cast)
					.map(entity -> new DeductionTime(
							new JpaFixedRestTZDeductionTimeGetMemento(entity.getStartTime(), entity.getEndTime())))
					.collect(Collectors.toList());
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
