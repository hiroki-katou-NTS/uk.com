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

/**
 * The Class JpaFixRestTimezoneSetGetMemento.
 */
public class JpaFixRestTimezoneSetGetMemento implements FixRestTimezoneSetGetMemento {

	/** The kshmt fixed half rest sets. */
	// KSHMT_FIXED_HALF_REST_SET
	private List<KshmtFixedHalfRestSet> kshmtFixedHalfRestSets;

	/**
	 * Instantiates a new jpa fix rest timezone set get memento.
	 *
	 * @param kshmtFixedHalfRestSets
	 *            the kshmt fixed half rest sets
	 */
	public JpaFixRestTimezoneSetGetMemento(List<KshmtFixedHalfRestSet> kshmtFixedHalfRestSets) {
		super();
		this.kshmtFixedHalfRestSets = kshmtFixedHalfRestSets;
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
		return this.kshmtFixedHalfRestSets.stream()
				.map(entity -> new DeductionTime(new JpaFixedRestTZDeductionTimeGetMemento(entity.getStartTime(), entity.getEndTime())))
				.collect(Collectors.toList());
	}

}
