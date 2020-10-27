/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;

/**
 * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
 */
public class JpaFlexHATzOFRTimeSetGetMemento implements TimezoneOfFixedRestTimeSetGetMemento{
	
	/** The entitys. */
	private KshmtWtFleBrFlWek entity;


	/**
	 * Instantiates a new jpa flex HA tz OFR time set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHATzOFRTimeSetGetMemento(KshmtWtFleBrFlWek entity) {
		super();
		this.entity = entity;
	}



	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento#getTimezones()
	 */
	@Override
	public List<DeductionTime> getTimezones() {
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleBrFiWekTss())) {
			return new ArrayList<>();
		}
		return this.entity.getKshmtWtFleBrFiWekTss().stream()
				.map(entity -> new DeductionTime(new JpaFlexHADeductionTimeGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
