/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreak;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;

/**
 * The Class JpaFixedWorkCalcSettingGetMemento.
 */
public class JpaFixedWorkCalcSettingGetMemento implements FixedWorkCalcSettingGetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa fixed work calc setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkCalcSettingGetMemento(KshmtFixedWorkSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento
	 * #getExceededPredAddVacationCalc()
	 */
	@Override
	public ExceededPredAddVacationCalc getExceededPredAddVacationCalc() {
		return new ExceededPredAddVacationCalc(new JpaExceededPredAddVacationCalcGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento
	 * #getOverTimeCalcNoBreak()
	 */
	@Override
	public OverTimeCalcNoBreak getOverTimeCalcNoBreak() {
		return new OverTimeCalcNoBreak(new JpaOverTimeCalcNoBreakGetMemento(this.entity));
	}

}
