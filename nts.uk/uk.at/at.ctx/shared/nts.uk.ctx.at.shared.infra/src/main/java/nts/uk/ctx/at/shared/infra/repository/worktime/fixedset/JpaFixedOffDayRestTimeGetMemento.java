/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTs;

/**
 * The Class JpaFixedOffDayRestTimeGetMemento.
 */
public class JpaFixedOffDayRestTimeGetMemento implements FixRestTimezoneSetGetMemento {

	/** The lst entity. */
	private List<KshmtWtFixBrHolTs> lstEntity;

	/**
	 * Instantiates a new jpa fixed off day rest time get memento.
	 *
	 * @param lstEntity
	 *            the lst entity
	 */
	public JpaFixedOffDayRestTimeGetMemento(List<KshmtWtFixBrHolTs> lstEntity) {
		super();
		this.lstEntity = lstEntity;
		if (CollectionUtil.isEmpty(this.lstEntity)) {
			this.lstEntity = new ArrayList<>();
		}
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
				.map(entity -> new DeductionTime(
						new JpaFixedRestTZDeductionTimeGetMemento<KshmtWtFixBrHolTs>(entity)))
				.sorted((item1, item2) -> item1.getStart().v() - item2.getStart().v())
				.collect(Collectors.toList());
	}

}
