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
 * The Class JpaFlexDeductionTimeGetMemento.
 */
public class JpaFlexHAFixDeductionTimeGetMemento implements DeductionTimeGetMemento{
	
	/** The entity. */
	private KshmtFlexHaFixRest entity;

	/**
	 * Instantiates a new jpa flex deduction time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHAFixDeductionTimeGetMemento(KshmtFlexHaFixRest entity) {
		super();
		if(entity.getKshmtFlexHaFixRestPK() == null){
			entity.setKshmtFlexHaFixRestPK(new KshmtFlexHaFixRestPK());
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
