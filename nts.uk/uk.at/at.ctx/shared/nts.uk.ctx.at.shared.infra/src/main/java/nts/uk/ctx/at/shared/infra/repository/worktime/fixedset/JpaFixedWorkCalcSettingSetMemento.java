/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreak;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;

/**
 * The Class JpaFixedWorkCalcSettingSetMemento.
 */
public class JpaFixedWorkCalcSettingSetMemento implements FixedWorkCalcSettingSetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa fixed work calc setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkCalcSettingSetMemento(KshmtFixedWorkSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento
	 * #setExceededPredAddVacationCalc(nts.uk.ctx.at.shared.dom.worktime.
	 * fixedset.ExceededPredAddVacationCalc)
	 */
	@Override
	public void setExceededPredAddVacationCalc(ExceededPredAddVacationCalc exceededPredAddVacationCalc) {
		exceededPredAddVacationCalc.saveToMemento(new JpaExceededPredAddVacationCalcSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento
	 * #setOverTimeCalcNoBreak(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeCalcNoBreak)
	 */
	@Override
	public void setOverTimeCalcNoBreak(OverTimeCalcNoBreak overTimeCalcNoBreak) {
		overTimeCalcNoBreak.saveToMemento(new JpaOverTimeCalcNoBreakSetMemento(this.entity));
	}
}
