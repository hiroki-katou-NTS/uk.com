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
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrWekTs;

/**
 * The Class JpaFixRestTimezoneSetGetMemento.
 *
 * @param <T> the generic type
 */
public class JpaFixRestHalfdayTzGetMemento implements FixRestTimezoneSetGetMemento {

	/** The entity sets. */
	private List<KshmtWtFixBrWekTs> entitySets;

	/**
	 * Instantiates a new jpa fix rest timezone set get memento.
	 *
	 * @param entitySets the entity sets
	 */
	public JpaFixRestHalfdayTzGetMemento(List<KshmtWtFixBrWekTs> entitySets) {
		super();
		this.entitySets = entitySets;
		if (CollectionUtil.isEmpty(this.entitySets)) {
			this.entitySets = new ArrayList<>();
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
		if (CollectionUtil.isEmpty(this.entitySets)){
		    return new ArrayList<>();
		}
		
		// KSHMT_WT_FIX_BR_WEK_TS
		return this.entitySets.stream()
				.map(entity -> new DeductionTime(
						new JpaFixedRestTZDeductionTimeGetMemento<KshmtWtFixBrWekTs>(entity)))
				.sorted((item1, item2) -> item1.getStart().v() - item2.getStart().v())
				.collect(Collectors.toList());	
	}

}
