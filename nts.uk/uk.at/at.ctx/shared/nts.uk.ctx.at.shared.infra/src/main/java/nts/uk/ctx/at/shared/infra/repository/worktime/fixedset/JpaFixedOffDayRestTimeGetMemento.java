/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSet;

/**
 * The Class JpaFixedOffDayRestTimeGetMemento.
 */
public class JpaFixedOffDayRestTimeGetMemento implements FixRestTimezoneSetGetMemento {

	/** The lst entity. */
	private List<KshmtFixedHolRestSet> lstEntity;

	/**
	 * Instantiates a new jpa fixed off day rest time get memento.
	 *
	 * @param lstEntity
	 *            the lst entity
	 */
	public JpaFixedOffDayRestTimeGetMemento(List<KshmtFixedHolRestSet> lstEntity) {
		this.lstEntity = lstEntity;
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
		return this.lstEntity.stream()
				.map(KshmtFixedHolRestSet.class::cast)
				.map(entity -> new DeductionTime(
						new JpaFixedRestTZDeductionTimeGetMemento<KshmtFixedHolRestSet>(entity)))
				.collect(Collectors.toList());
	}

}
