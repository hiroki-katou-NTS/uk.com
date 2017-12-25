/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdFixRest;

/**
 * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
 */
public class JpaFlexODTzOFRTimeSetGetMemento implements TimezoneOfFixedRestTimeSetGetMemento{
	
	/** The entitys. */
	private KshmtFlexOdFixRest entity;


	/**
	 * Instantiates a new jpa flex OD tz OFR time set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODTzOFRTimeSetGetMemento(KshmtFlexOdFixRest entity) {
		super();
		this.entity = entity;
	}



	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento#getTimezones()
	 */
	@Override
	public List<DeductionTime> getTimezones() {
		/*
		 * return this.entitys.stream().map(entity -> new DeductionTime(new
		 * JpaFlexODDeductionTimeGetMemento(entity)))
		 * .collect(Collectors.toList());
		 */
		return new ArrayList<>();
	}

}
