/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFl;

/**
 * The Class JpaTimezoneOfFixedRestTimeSetGetMemento.
 */
public class JpaTimezoneOfFixedRestTimeSetGetMemento implements TimezoneOfFixedRestTimeSetGetMemento {

	/** The entity. */
	private KshmtWtFloBrFl entity;
	
	/**
	 * Instantiates a new jpa timezone of fixed rest time set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaTimezoneOfFixedRestTimeSetGetMemento(KshmtWtFloBrFl entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFloBrFiAllTs())) {
			this.entity.setLstKshmtWtFloBrFiAllTs(new ArrayList<>());
		}
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento#getTimezones()
	 */
	@Override
	public List<DeductionTime> getTimezones() {
		return this.entity.getLstKshmtWtFloBrFiAllTs().stream()
			.map(entity -> new DeductionTime(new JpaDeductionTimeGetMemento(entity)))
			.sorted((item1, item2) -> item1.getStart().compareTo(item2.getStart()))
			.collect(Collectors.toList());
	}

}
