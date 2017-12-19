/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaFixRest;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaFixRestPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexOffdayDeductionTimeGetMemento.
 */
public class JpaFlexOffdayDeductionTimeGetMemento implements DeductionTimeGetMemento{
	
	/** The entity. */
	private KshmtFlexHaFixRest entity;
	
	/**
	 * Instantiates a new jpa flex offday deduction time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexOffdayDeductionTimeGetMemento(KshmtFlexHaFixRest entity) {
		super();
		if(entity.getKshmtFlexHaFixRestPK() == null){
			entity.setKshmtFlexHaFixRestPK(new KshmtFlexHaFixRestPK());
		}
		this.entity = entity;
	}


	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getStrTime());
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}
	
	

}
