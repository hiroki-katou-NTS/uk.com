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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHol;

/**
 * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
 */
public class JpaFlexODTzOFRTimeSetGetMemento implements TimezoneOfFixedRestTimeSetGetMemento{
	
	/** The entitys. */
	private KshmtWtFleBrFlHol entity;

	/**
	 * Instantiates a new jpa flex OD tz OFR time set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODTzOFRTimeSetGetMemento(KshmtWtFleBrFlHol entity) {
		super();
		this.entity = entity;
	}



	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento#getTimezones()
	 */
	@Override
	public List<DeductionTime> getTimezones() {
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleBrFiHolTss())) {
			return new ArrayList<>();
		}
		return this.entity.getKshmtWtFleBrFiHolTss().stream()
				.map(entity -> new DeductionTime(new JpaFlexODDeductionTimeGetMemento(entity)))
				.collect(Collectors.toList());

	}

}
