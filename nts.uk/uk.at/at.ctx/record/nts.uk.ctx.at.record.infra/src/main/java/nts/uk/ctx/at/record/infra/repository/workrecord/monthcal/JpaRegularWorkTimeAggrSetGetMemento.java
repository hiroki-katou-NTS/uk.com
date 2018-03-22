/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstRegMCalSet;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaRegularWorkTimeAggrSetGetMemento<T extends KrcstRegMCalSet> implements RegularWorkTimeAggrSetGetMemento {

	/** The type value. */
	private T typeValue;
	
	@Override
	public ExcessOutsideTimeSetReg getAggregateTimeSet() {
		return null;
	}

	@Override
	public ExcessOutsideTimeSetReg getExcessOutsideTimeSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
