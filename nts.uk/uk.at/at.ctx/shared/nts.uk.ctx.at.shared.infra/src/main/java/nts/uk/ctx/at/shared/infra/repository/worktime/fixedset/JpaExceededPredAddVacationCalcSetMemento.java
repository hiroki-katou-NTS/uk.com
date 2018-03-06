/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalcSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;

/**
 * The Class JpaExceededPredAddVacationCalcSetMemento.
 */
public class JpaExceededPredAddVacationCalcSetMemento implements ExceededPredAddVacationCalcSetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa exceeded pred add vacation calc set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaExceededPredAddVacationCalcSetMemento(KshmtFixedWorkSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcSetMemento#setCalcMethod(nts.uk.ctx.at.shared.
	 * dom.worktime.common.CalcMethodExceededPredAddVacation)
	 */
	@Override
	public void setCalcMethod(CalcMethodExceededPredAddVacation calcMethod) {
		if (calcMethod != null) {
			this.entity.setExceededPredCalcMethod(calcMethod.value);
		}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcSetMemento#setOtFrameNo(nts.uk.ctx.at.shared.
	 * dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo)
	 */
	@Override
	public void setOtFrameNo(OTFrameNo otFrameNo) {
		if (otFrameNo != null) {
			this.entity.setExceededPredOtFrameNo(otFrameNo.v());
		}	
	}
}
