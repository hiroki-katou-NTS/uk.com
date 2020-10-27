/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleWorkTsPK;

/**
 * The Class JpaFlexEmTimeZoneSetSetMemento.
 */
public class JpaFlexEmTimeZoneSetSetMemento implements EmTimeZoneSetSetMemento{
	
	/** The entity. */
	private KshmtWtFleWorkTs entity;
	

	/**
	 * Instantiates a new jpa flex em time zone set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexEmTimeZoneSetSetMemento(KshmtWtFleWorkTs entity) {
		super();
		if (entity.getKshmtWtFleWorkTsPK() == null) {
			entity.setKshmtWtFleWorkTsPK(new KshmtWtFleWorkTsPK());
		}
		this.entity = entity;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetSetMemento#
	 * setEmploymentTimeFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * EmTimeFrameNo)
	 */
	@Override
	public void setEmploymentTimeFrameNo(EmTimeFrameNo no) {
		this.entity.getKshmtWtFleWorkTsPK().setTimeFrameNo(no.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetSetMemento#
	 * setTimezone(nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding)
	 */
	@Override
	public void setTimezone(TimeZoneRounding rounding) {
		if(rounding!=null){
			rounding.saveToMemento(new JpaFlexTimeZoneRoundingSetMemento(this.entity));
		}
		
	}
	

}
