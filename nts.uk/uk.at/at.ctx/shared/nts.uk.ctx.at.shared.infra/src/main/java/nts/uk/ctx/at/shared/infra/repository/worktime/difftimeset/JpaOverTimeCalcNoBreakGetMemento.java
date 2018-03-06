/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;

/**
 * The Class JpaOverTimeCalcNoBreakGetMemento.
 */
public class JpaOverTimeCalcNoBreakGetMemento implements OverTimeCalcNoBreakGetMemento {

	/** The entity. */
	private KshmtDiffTimeWorkSet entity;

	/**
	 * Instantiates a new jpa over time calc no break get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaOverTimeCalcNoBreakGetMemento(KshmtDiffTimeWorkSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento#
	 * getCalcMethod()
	 */
	@Override
	public CalcMethodNoBreak getCalcMethod() {
		if (this.entity.getOtCalcMethod() == null) {
			return null;
		}
		return CalcMethodNoBreak.valueOf(this.entity.getOtCalcMethod());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento#
	 * getInLawOT()
	 */
	@Override
	public OTFrameNo getInLawOT() {
		if (this.entity.getOtInLaw() == null) {
			return null;
		}
		return new OTFrameNo(this.entity.getOtInLaw());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento#
	 * getNotInLawOT()
	 */
	@Override
	public OTFrameNo getNotInLawOT() {
		if (this.entity.getOtNotInLaw() == null) {
			return null;
		}
		return new OTFrameNo(this.entity.getOtNotInLaw());
	}
}
