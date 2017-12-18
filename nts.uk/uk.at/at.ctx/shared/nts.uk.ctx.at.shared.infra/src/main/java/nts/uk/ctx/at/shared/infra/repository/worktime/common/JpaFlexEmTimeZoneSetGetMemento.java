/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkTimeSet;

/**
 * The Class JpaFlexEmTimeZoneSetGetMemento.
 */
public class JpaFlexEmTimeZoneSetGetMemento implements EmTimeZoneSetGetMemento{
	
	/** The entity. */
	private KshmtFlexWorkTimeSet entity;
	

	/**
	 * Instantiates a new jpa flex em time zone set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexEmTimeZoneSetGetMemento(KshmtFlexWorkTimeSet entity) {
		super();
		this.entity = entity;
	}

	@Override
	public EmTimeFrameNo getEmploymentTimeFrameNo() {
		return null;
	}

	@Override
	public TimeZoneRounding getTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

}
