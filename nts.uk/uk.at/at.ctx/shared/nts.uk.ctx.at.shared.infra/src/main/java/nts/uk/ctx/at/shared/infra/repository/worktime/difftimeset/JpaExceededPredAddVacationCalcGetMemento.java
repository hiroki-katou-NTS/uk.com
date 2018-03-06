/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalcGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;

/**
 * The Class JpaExceededPredAddVacationCalcGetMemento.
 */
public class JpaExceededPredAddVacationCalcGetMemento implements ExceededPredAddVacationCalcGetMemento {

	/** The entity. */
	private KshmtDiffTimeWorkSet entity;

	/**
	 * Instantiates a new jpa exceeded pred add vacation calc get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaExceededPredAddVacationCalcGetMemento(KshmtDiffTimeWorkSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcGetMemento#getCalcMethod()
	 */
	@Override
	public CalcMethodExceededPredAddVacation getCalcMethod() {
		if (this.entity.getExceededPredCalcMethod() == null) {
			return null;
		}
		return CalcMethodExceededPredAddVacation.valueOf(this.entity.getExceededPredCalcMethod());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcGetMemento#getOtFrameNo()
	 */
	@Override
	public OTFrameNo getOtFrameNo() {
		if (this.entity.getExceededPredOtFrameNo() == null) {
			return null;
		}
		return new OTFrameNo(this.entity.getExceededPredOtFrameNo());
	}

}
