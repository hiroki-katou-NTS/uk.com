/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaOverTimeCalcNoBreakGetMemento.
 */
public class JpaOverTimeCalcNoBreakGetMemento<T extends ContractUkJpaEntity> implements OverTimeCalcNoBreakGetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa over time calc no break get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaOverTimeCalcNoBreakGetMemento(T entity) {
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
		if (this.entity instanceof KshmtWtFix) {
			if (((KshmtWtFix) this.entity).getOtCalcMethod() == null) {
				return null;
			}
			return CalcMethodNoBreak.valueOf(((KshmtWtFix) this.entity).getOtCalcMethod());
		}
		if (this.entity instanceof KshmtWtDif) {
			if (((KshmtWtDif) this.entity).getOtCalcMethod() == null) {
				return null;
			}
			return CalcMethodNoBreak.valueOf(((KshmtWtDif) this.entity).getOtCalcMethod());
		}
		throw new IllegalStateException("entity type is not valid");
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
		if (this.entity instanceof KshmtWtFix) {
			if (((KshmtWtFix) this.entity).getOtInLaw() == null) {
				return null;
			}
			return new OTFrameNo(((KshmtWtFix) this.entity).getOtInLaw());
		}
		if (this.entity instanceof KshmtWtDif) {
			if (((KshmtWtDif) this.entity).getOtInLaw() == null) {
				return null;
			}
			return new OTFrameNo(((KshmtWtDif) this.entity).getOtInLaw());
		}
		throw new IllegalStateException("entity type is not valid");
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
		if (this.entity instanceof KshmtWtFix) {
			if (((KshmtWtFix) this.entity).getOtNotInLaw() == null) {
				return null;
			}
			return new OTFrameNo(((KshmtWtFix) this.entity).getOtNotInLaw());
		}
		if (this.entity instanceof KshmtWtDif) {
			if (((KshmtWtDif) this.entity).getOtNotInLaw() == null) {
				return null;
			}
			return new OTFrameNo(((KshmtWtDif) this.entity).getOtNotInLaw());
		}
		throw new IllegalStateException("entity type is not valid");
	}
}
