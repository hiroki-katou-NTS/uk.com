/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaStampBreakCalculationSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaStampBreakCalculationSetMemento<T extends ContractUkJpaEntity> implements StampBreakCalculationSetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa stamp break calculation set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaStampBreakCalculationSetMemento(T entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationSetMemento
	 * #setUsePrivateGoOutRest(boolean)
	 */
	@Override
	public void setUsePrivateGoOutRest(boolean val) {
		if (this.entity instanceof KshmtWtFloBrFlAll) {
			((KshmtWtFloBrFlAll) this.entity).setUserPrivateGoOutRest(BooleanGetAtr.getAtrByBoolean(val));
			return;
		}
		if (this.entity instanceof KshmtWtFleBrFl) {
			((KshmtWtFleBrFl) this.entity).setUserPrivateGoOutRest(BooleanGetAtr.getAtrByBoolean(val));
			return;
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationSetMemento
	 * #setUseAssoGoOutRest(boolean)
	 */
	@Override
	public void setUseAssoGoOutRest(boolean val) {
		if (this.entity instanceof KshmtWtFloBrFlAll) {
			((KshmtWtFloBrFlAll) this.entity).setUserAssoGoOutRest(BooleanGetAtr.getAtrByBoolean(val));
			return;
		}
		if (this.entity instanceof KshmtWtFleBrFl) {
			((KshmtWtFleBrFl) this.entity).setUserAssoGoOutRest(BooleanGetAtr.getAtrByBoolean(val));
			return;
		}
		throw new IllegalStateException("entity type is not valid");
	}
}
