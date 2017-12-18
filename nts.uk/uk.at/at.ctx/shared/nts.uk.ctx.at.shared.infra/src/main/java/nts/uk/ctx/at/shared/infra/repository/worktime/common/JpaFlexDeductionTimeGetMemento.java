/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexFixRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexFixRestSetPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexDeductionTimeGetMemento.
 */
public class JpaFlexDeductionTimeGetMemento implements DeductionTimeGetMemento{
	
	/** The entity. */
	private KshmtFlexFixRestSet entity;

	/**
	 * Instantiates a new jpa flex deduction time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexDeductionTimeGetMemento(KshmtFlexFixRestSet entity) {
		super();
		if(entity.getKshmtFlexFixRestSetPK() == null){
			entity.setKshmtFlexFixRestSetPK(new KshmtFlexFixRestSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getStart
	 * ()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getStrTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}
	

}
