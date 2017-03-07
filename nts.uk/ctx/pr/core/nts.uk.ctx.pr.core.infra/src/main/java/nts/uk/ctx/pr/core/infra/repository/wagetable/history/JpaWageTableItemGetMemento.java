/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.math.BigDecimal;

import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableMny;

/**
 * The Class JpaWageTableItemGetMemento.
 */
public class JpaWageTableItemGetMemento implements WageTableItemGetMemento {

	/** The type value. */
	protected QwtmtWagetableMny typeValue;

	/**
	 * Instantiates a new jpa wage table item get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableItemGetMemento(QwtmtWagetableMny typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento#
	 * getElement1Id()
	 */
	@Override
	public ElementId getElement1Id() {
		return new ElementId(this.typeValue.getQwtmtWagetableMnyPK().getElement1Id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento#
	 * getElement2Id()
	 */
	@Override
	public ElementId getElement2Id() {
		return new ElementId(this.typeValue.getQwtmtWagetableMnyPK().getElement2Id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento#
	 * getElement3Id()
	 */
	@Override
	public ElementId getElement3Id() {
		return new ElementId(this.typeValue.getQwtmtWagetableMnyPK().getElement3Id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItemGetMemento#
	 * getAmount()
	 */
	@Override
	public BigDecimal getAmount() {
		return new BigDecimal(this.typeValue.getValueMny());
	}

}
