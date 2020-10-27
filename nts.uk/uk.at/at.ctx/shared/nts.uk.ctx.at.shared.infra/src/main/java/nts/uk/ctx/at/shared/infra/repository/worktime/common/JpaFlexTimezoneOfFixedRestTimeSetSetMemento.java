/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiWekTs;

/**
 * The Class JpaFlexTimezoneOfFixedRestTimeSetSetMemento.
 */
public class JpaFlexTimezoneOfFixedRestTimeSetSetMemento implements TimezoneOfFixedRestTimeSetSetMemento {

	/** The entities. */
	 List<KshmtWtFleBrFiWekTs> entities;

	/**
	 * Instantiates a new jpa flex timezone of fixed rest time set set memento.
	 *
	 * @param entities
	 *            the entities
	 */
	public JpaFlexTimezoneOfFixedRestTimeSetSetMemento(List<KshmtWtFleBrFiWekTs> entities) {
		super();
		this.entities = entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * TimezoneOfFixedRestTimeSetSetMemento#setTimezones(java.util.List)
	 */
	@Override
	public void setTimezones(List<DeductionTime> timzones) {
		if (CollectionUtil.isEmpty(timzones)) {
			this.entities = new ArrayList<>();
		} else {
			this.entities = timzones.stream().map(domain -> {
				KshmtWtFleBrFiWekTs entity = new KshmtWtFleBrFiWekTs();
				domain.saveToMemento(new JpaFlexHADeductionTimeSetMemento(entity));
				return entity;
			}).collect(Collectors.toList());
		}
	}

}
