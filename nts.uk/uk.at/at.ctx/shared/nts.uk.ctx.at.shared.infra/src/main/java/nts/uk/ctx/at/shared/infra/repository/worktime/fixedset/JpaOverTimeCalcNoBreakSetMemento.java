/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;

/**
 * The Class JpaOverTimeCalcNoBreakSetMemento.
 */
public class JpaOverTimeCalcNoBreakSetMemento implements OverTimeCalcNoBreakSetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa over time calc no break set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaOverTimeCalcNoBreakSetMemento(KshmtFixedWorkSet entity) {
		super();
		this.entity = entity;
	}

	/**
	 * Sets the calc method.
	 *
	 * @param calcMethod
	 *            the new calc method
	 */
	@Override
	public void setCalcMethod(CalcMethodNoBreak calcMethod) {
		if (calcMethod != null) {
			this.entity.setOtCalcMethod(calcMethod.value);
		}
	}

	/**
	 * Sets the in law OT.
	 *
	 * @param inLawOT
	 *            the new in law OT
	 */
	@Override
	public void setInLawOT(OTFrameNo inLawOT) {
		if (inLawOT != null) {
			this.entity.setOtInLaw(inLawOT.v());
		}
	}

	/**
	 * Sets the not in law OT.
	 *
	 * @param notInLawOT
	 *            the new not in law OT
	 */
	@Override
	public void setNotInLawOT(OTFrameNo notInLawOT) {
		if (notInLawOT != null) {
			this.entity.setOtNotInLaw(notInLawOT.v());
		}
	}
}
