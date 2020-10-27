/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalcGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaExceededPredAddVacationCalcGetMemento.
 *
 * @param <T> the generic type
 */
public class JpaExceededPredAddVacationCalcGetMemento<T extends ContractUkJpaEntity>
		implements ExceededPredAddVacationCalcGetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa exceeded pred add vacation calc get memento.
	 *
	 * @param entity the entity
	 */
	public JpaExceededPredAddVacationCalcGetMemento(T entity) {
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
		if (this.entity instanceof KshmtWtFix) {
			if (((KshmtWtFix) this.entity).getExceededPredCalcMethod() == null) {
				return null;
			}
			return CalcMethodExceededPredAddVacation
					.valueOf(((KshmtWtFix) this.entity).getExceededPredCalcMethod());
		}
		if (this.entity instanceof KshmtWtDif) {
			if (((KshmtWtDif) this.entity).getExceededPredCalcMethod() == null) {
				return null;
			}
			return CalcMethodExceededPredAddVacation
					.valueOf(((KshmtWtDif) this.entity).getExceededPredCalcMethod());
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * ExceededPredAddVacationCalcGetMemento#getOtFrameNo()
	 */
	@Override
	public OTFrameNo getOtFrameNo() {
		if (this.entity instanceof KshmtWtFix) {
			if (((KshmtWtFix) this.entity).getExceededPredOtFrameNo() == null) {
				return null;
			}
			return new OTFrameNo(((KshmtWtFix) this.entity).getExceededPredOtFrameNo());
		}
		if (this.entity instanceof KshmtWtDif) {
			if (((KshmtWtDif) this.entity).getExceededPredOtFrameNo() == null) {
				return null;
			}
			return new OTFrameNo(((KshmtWtDif) this.entity).getExceededPredOtFrameNo());
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
