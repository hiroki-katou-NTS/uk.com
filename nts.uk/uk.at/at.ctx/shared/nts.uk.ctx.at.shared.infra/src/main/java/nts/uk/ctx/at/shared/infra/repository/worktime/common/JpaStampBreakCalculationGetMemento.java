/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaStampBreakCalculationGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaStampBreakCalculationGetMemento<T extends ContractUkJpaEntity> implements StampBreakCalculationGetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa stamp break calculation get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaStampBreakCalculationGetMemento(T entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationGetMemento
	 * #getUsePrivateGoOutRest()
	 */
	@Override
	public boolean getUsePrivateGoOutRest() {
		if (this.entity instanceof KshmtWtFloBrFlAll) {
			return BooleanGetAtr.getAtrByInteger(((KshmtWtFloBrFlAll) this.entity).getUserPrivateGoOutRest());
		}
		if (this.entity instanceof KshmtWtFleBrFl) {
			return BooleanGetAtr.getAtrByInteger(((KshmtWtFleBrFl) this.entity).getUserPrivateGoOutRest());
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationGetMemento
	 * #getUseAssoGoOutRest()
	 */
	@Override
	public boolean getUseAssoGoOutRest() {
		if (this.entity instanceof KshmtWtFloBrFlAll) {
			return BooleanGetAtr.getAtrByInteger(((KshmtWtFloBrFlAll) this.entity).getUserAssoGoOutRest());
		}
		if (this.entity instanceof KshmtWtFleBrFl) {
			return BooleanGetAtr.getAtrByInteger(((KshmtWtFleBrFl) this.entity).getUserAssoGoOutRest());
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
