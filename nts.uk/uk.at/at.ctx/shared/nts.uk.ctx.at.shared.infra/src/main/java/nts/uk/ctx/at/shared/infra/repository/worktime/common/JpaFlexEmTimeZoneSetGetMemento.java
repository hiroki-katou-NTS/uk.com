/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleWorkTs;

/**
 * The Class JpaFlexEmTimeZoneSetGetMemento.
 */
public class JpaFlexEmTimeZoneSetGetMemento implements EmTimeZoneSetGetMemento{
	
	/** The entity. */
	private KshmtWtFleWorkTs entity;
	

	/**
	 * Instantiates a new jpa flex em time zone set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexEmTimeZoneSetGetMemento(KshmtWtFleWorkTs entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetGetMemento#getEmploymentTimeFrameNo()
	 */
	@Override
	public EmTimeFrameNo getEmploymentTimeFrameNo() {
		return new EmTimeFrameNo(this.entity.getKshmtWtFleWorkTsPK().getTimeFrameNo());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetGetMemento#getTimezone()
	 */
	@Override
	public TimeZoneRounding getTimezone() {
		return new TimeZoneRounding(new JpaFlexTimeZoneRoundingGetMemento(this.entity));
	}
	

}
